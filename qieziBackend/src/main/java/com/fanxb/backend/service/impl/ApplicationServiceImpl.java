package com.fanxb.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
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
    public void visit(HttpServletRequest request, HttpServletResponse response, String callBack, String key) throws IOException {
        String refer = request.getHeader("Referer");
        if (StrUtil.isEmpty(refer)) {
            throw new CustomBaseException("未获取到来源路径");
        }
        String path;
        try {
            URL url = new URL(refer);
            path = StrUtil.isEmpty(url.getPath()) ? "/" : url.getPath();
        } catch (Exception e) {
            throw new CustomBaseException("url解析错误", e);
        }
        if (path.length() > 100) {
            throw new CustomBaseException("路径长度不能大于100," + path);
        }

        int hostId = getHostId(key);
        HostPo uvPvData = hostDao.getUvPvById(hostId);
        DetailPagePo detailUvPvData = detailPageDao.getUvPvById(hostId, path);
        if (detailUvPvData == null) {
            detailUvPvData = new DetailPagePo().setHostId(hostId).setPath(path).setUv(1).setPv(1);
        }
        UvPvVo uvPvVo = new UvPvVo(uvPvData.getUv(), uvPvData.getPv(), detailUvPvData.getUv(), detailUvPvData.getPv());
        String uvPvVoStr = JSON.toJSONString(uvPvVo);
        String res = String.format("try{%s(%s);}catch(e){console.error(e);console.log(%s)}", callBack, uvPvVoStr, uvPvVoStr);
        response.setHeader(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue());
        response.getOutputStream().write(res.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();

        //异步数据更新
        DetailPagePo finalDetailUvPvData = detailUvPvData;
        ThreadPoolUtil.execute(() -> updateData(NetUtil.getClientIp(request), uvPvData, finalDetailUvPvData));
    }

    /**
     * 更新数据
     *
     * @param ip           来源ip
     * @param hostPo       hostPo
     * @param detailPagePo detailPagePo
     * @author fanxb
     * date 2022/2/16 15:40
     */
    private void updateData(String ip, HostPo hostPo, DetailPagePo detailPagePo) {
        String hostKey = RedisConstant.HOST_UV_PRE + hostPo.getId() + ip;
        String hostVal = stringRedisTemplate.opsForValue().get(hostKey);
        hostDao.updateUvPv(hostPo.getId(), hostVal == null ? 1 : 0);
        long tomorrowZero = LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (hostVal == null) {
            stringRedisTemplate.opsForValue().set(hostKey, "", tomorrowZero - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        String pageKey = RedisConstant.PAGE_UV_PRE + hostPo.getId() + ip + detailPagePo.getPath();
        String pageVal = stringRedisTemplate.opsForValue().get(pageKey);
        if (detailPagePo.getId() == null) {
            detailPageDao.insertOne(detailPagePo);
        } else {
            detailPageDao.updateUvPv(detailPagePo.getId(), pageVal == null ? 1 : 0);
        }
        if (pageVal == null) {
            stringRedisTemplate.opsForValue().set(pageKey, "", tomorrowZero - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
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
