package com.fanxb.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import com.alibaba.fastjson.JSON;
import com.fanxb.backend.constants.CommonConstant;
import com.fanxb.backend.constants.RedisConstant;
import com.fanxb.backend.dao.DetailPageDao;
import com.fanxb.backend.dao.DetailPageDayDao;
import com.fanxb.backend.dao.HostDao;
import com.fanxb.backend.dao.HostDayDao;
import com.fanxb.backend.entity.dto.ApplicationSignDto;
import com.fanxb.backend.entity.exception.CustomBaseException;
import com.fanxb.backend.entity.po.DetailPageDayPo;
import com.fanxb.backend.entity.po.DetailPagePo;
import com.fanxb.backend.entity.po.HostDayPo;
import com.fanxb.backend.entity.po.HostPo;
import com.fanxb.backend.entity.vo.ApplicationSignVo;
import com.fanxb.backend.entity.vo.UvPvVo;
import com.fanxb.backend.service.ApplicationService;
import com.fanxb.backend.util.NetUtil;
import com.fanxb.backend.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 应用管理
 *
 * @author fanxb
 * @date 2022/2/15 16:34
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final StringRedisTemplate stringRedisTemplate;
    private final HostDao hostDao;
    private final DetailPageDao detailPageDao;
    private final HostDayDao hostDayDao;
    private final DetailPageDayDao detailPageDayDao;

    @Autowired
    public ApplicationServiceImpl(StringRedisTemplate stringRedisTemplate, HostDao hostDao, DetailPageDao detailPageDao, HostDayDao hostDayDao, DetailPageDayDao detailPageDayDao) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.hostDao = hostDao;
        this.detailPageDao = detailPageDao;
        this.hostDayDao = hostDayDao;
        this.detailPageDayDao = detailPageDayDao;
    }

    @Override
    public ApplicationSignVo sign(ApplicationSignDto signDto) {
        String redisKey = RedisConstant.APPLICATION_SIGN_PRE + signDto.getCode();
        if (!CommonConstant.IS_DEV && stringRedisTemplate.opsForValue().get(redisKey) == null) {
            throw new CustomBaseException("验证码错误");
        }
        stringRedisTemplate.delete(redisKey);
        HostPo po = HostPo.builder().key(IdUtil.fastSimpleUUID()).secret(IdUtil.fastSimpleUUID())
                .host(signDto.getHost())
                .name(signDto.getName()).build();
        hostDao.insertOne(po);
        stringRedisTemplate.opsForValue().set(RedisConstant.KEY_ID_PRE + po.getKey(), String.valueOf(po.getId()));
        return new ApplicationSignVo(po.getKey(), po.getSecret());
    }

    @Override
    public boolean check(ApplicationSignVo body) {
        return hostDao.exist(body.getKey(), body.getSecret());
    }

    private static Pattern PATTERN = Pattern.compile("googlebot|bingbot|yandex|baiduspider|360Spider|Sogou Spider|Bytespider|twitterbot|facebookexternalhit|rogerbot|linkedinbot|embedly|quora link preview|showyoubot|outbrain|pinterest\\/0\\.|pinterestbot|slackbot|vkShare|W3C_Validator|whatsapp");

    @Override
    public void visit(HttpServletRequest request, HttpServletResponse response, String callBack, String key, String path, boolean notAdd) throws IOException {
        //检查是否搜索引擎的请求
        String agent = request.getHeader(HttpHeaders.USER_AGENT);
        if (agent != null && PATTERN.matcher(agent.toLowerCase(Locale.ROOT)).find()) {
            //搜索引擎的直接返回
            return;
        }
        int hostId = getHostId(key);
        HostPo hostData = hostDao.getUvPvById(hostId);
        DetailPagePo detailData = detailPageDao.getUvPvById(hostId, path);
        if (detailData == null) {
            detailData = new DetailPagePo().setHostId(hostId).setPath(path).setUv(0).setPv(0);
        }
        //数据计算更新
        if (!notAdd) {
            updateData(NetUtil.getClientIp(request), hostData, detailData);
        }
        //写出
        UvPvVo uvPvVo = new UvPvVo(hostData.getUv(), hostData.getPv(), detailData.getUv(), detailData.getPv());
        String uvPvVoStr = JSON.toJSONString(uvPvVo);
        String res = String.format("try{%s(%s);}catch(e){console.error(e);console.log(%s)}", callBack, uvPvVoStr, uvPvVoStr);
        response.setHeader(Header.CONTENT_TYPE.getValue(), "text/javascript");
        response.getOutputStream().write(res.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
    }

    /**
     * 更新数据,pv:每次都增加，uv:24小时内同一ip,0-24点只统计一次
     *
     * @param ip           来源ip
     * @param hostPo       hostPo
     * @param detailPagePo detailPagePo
     * @author fanxb
     */
    private void updateData(String ip, HostPo hostPo, DetailPagePo detailPagePo) {
        String hostKey = RedisConstant.HOST_UV_PRE + hostPo.getId() + "_" + ip;
        //记录ip是否在当天访问过host
        String hostVal = stringRedisTemplate.opsForValue().get(hostKey);
        hostPo.setPv(hostPo.getPv() + 1);
        long tomorrowZero = LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (hostVal == null) {
            stringRedisTemplate.opsForValue().set(hostKey, "", tomorrowZero - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            hostPo.setUv(hostPo.getUv() + 1);
        }
        String pageKey = RedisConstant.PAGE_UV_PRE + hostPo.getId() + ip + detailPagePo.getPath();
        //记录ip是否在当天访问过页面
        String pageVal = stringRedisTemplate.opsForValue().get(pageKey);
        detailPagePo.setPv(detailPagePo.getPv() + 1);
        if (pageVal == null) {
            stringRedisTemplate.opsForValue().set(pageKey, "", tomorrowZero - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            detailPagePo.setUv(detailPagePo.getUv() + 1);
        }
        //异步更新sql
        ThreadPoolUtil.execute(() -> {
            hostDao.updateUvPv(hostPo.getId(), hostVal == null ? 1 : 0);
            if (detailPagePo.getId() == null) {
                detailPageDao.insertOne(detailPagePo);
            } else {
                detailPageDao.updateUvPv(detailPagePo.getId(), pageVal == null ? 1 : 0);
            }
            //更新站点的日pv,uv
            int dayNum = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE).replaceAll("-", ""));
            hostDayDao.updatePvUv(getDayTableId(hostPo.getId(), dayNum, RedisConstant.DAY_HOST_ID_PRE), 1, hostVal == null ? 1 : 0);
            detailPageDayDao.updatePvUv(getDayTableId(detailPagePo.getId(), dayNum, RedisConstant.DAY_DETAIL_PAGE_ID_PRE), 1, pageVal == null ? 1 : 0);
        });
    }

    /**
     * 获取表id，数据行不存在时新增行
     *
     * @param id   id
     * @param day  day(20220202)
     * @param type 类型
     * @author fanxb
     */
    private int getDayTableId(int id, int day, String type) {
        String key = type + "_" + id + "_" + day;
        String val = stringRedisTemplate.opsForValue().get(key);
        if (val != null) {
            return Integer.parseInt(val);
        }
        //redis中没有,从数据库中查找
        Integer dbId = RedisConstant.DAY_HOST_ID_PRE.equals(type) ? hostDayDao.getId(id, day) : detailPageDayDao.getId(id, day);
        if (dbId == null) {
            //TODO 此次应该加锁，避免并发情况下重复创建报错
            dbId = RedisConstant.DAY_HOST_ID_PRE.equals(type) ? hostDayDao.getId(id, day) : detailPageDayDao.getId(id, day);
            if (dbId == null) {
                //数据库中也没有，需要先初始化数据库中数据
                if (RedisConstant.DAY_HOST_ID_PRE.equals(type)) {
                    HostDayPo hostDayPo = new HostDayPo().setHostId(id).setDayNum(day);
                    hostDayDao.insert(hostDayPo);
                    dbId = hostDayPo.getId();
                } else {
                    DetailPageDayPo detailPageDayPo = new DetailPageDayPo().setDetailPageId(id).setDayNum(day);
                    detailPageDayDao.insert(detailPageDayPo);
                    dbId = detailPageDayPo.getId();
                }
            }
        }
        stringRedisTemplate.opsForValue().set(key, dbId.toString());
        return dbId;
    }

    /**
     * 获取hostID
     *
     * @param key key
     * @return {@link int}
     * @author fanxb
     */
    private int getHostId(String key) {
        String id = stringRedisTemplate.opsForValue().get(key);
        if (id != null) {
            return Integer.parseInt(id);
        }
        Integer hostId = hostDao.getIdByKey(key);
        if (hostId == null) {
            throw new CustomBaseException("key无效:" + key);
        }
        stringRedisTemplate.opsForValue().set(key, hostId.toString(), 2, TimeUnit.DAYS);
        return hostId;
    }

}
