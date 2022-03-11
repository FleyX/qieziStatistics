package com.fanxb.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import com.alibaba.fastjson.JSON;
import com.fanxb.backend.constants.RedisConstant;
import com.fanxb.backend.constants.CommonConstant;
import com.fanxb.backend.dao.DetailPageDao;
import com.fanxb.backend.dao.HostDao;
import com.fanxb.backend.entity.dto.ApplicationSignDto;
import com.fanxb.backend.entity.exception.CustomBaseException;
import com.fanxb.backend.entity.po.DetailPagePo;
import com.fanxb.backend.entity.po.HostPo;
import com.fanxb.backend.entity.vo.ApplicationSignVo;
import com.fanxb.backend.entity.vo.UvPvVo;
import com.fanxb.backend.service.ApplicationService;
import com.fanxb.backend.util.NetUtil;
import com.fanxb.backend.util.ThreadPoolUtil;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    public ApplicationServiceImpl(StringRedisTemplate stringRedisTemplate, HostDao hostDao, DetailPageDao detailPageDao) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.hostDao = hostDao;
        this.detailPageDao = detailPageDao;
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
    public void visit(HttpServletRequest request, HttpServletResponse response, String callBack, String key, String path, boolean notAdd) throws IOException {
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
     * date 2022/2/16 15:40
     */
    private void updateData(String ip, HostPo hostPo, DetailPagePo detailPagePo) {
        String hostKey = RedisConstant.HOST_UV_PRE + hostPo.getId() + "_" + ip;
        String hostVal = stringRedisTemplate.opsForValue().get(hostKey);
        hostPo.setPv(hostPo.getPv() + 1);
        long tomorrowZero = LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (hostVal == null) {
            stringRedisTemplate.opsForValue().set(hostKey, "", tomorrowZero - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            hostPo.setUv(hostPo.getUv() + 1);
        }
        String pageKey = RedisConstant.PAGE_UV_PRE + hostPo.getId() + ip + detailPagePo.getPath();
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
        });
    }

    /**
     * 获取hostID
     *
     * @param key key
     * @return {@link int}
     * @author fanxb
     * date 2022/2/16 15:37
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
        stringRedisTemplate.opsForValue().set(key, hostId.toString());
        return hostId;
    }


}
