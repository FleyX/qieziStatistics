package com.fanxb.backend.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.IdUtil;
import com.fanxb.backend.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码service
 *
 * @author fanxb
 * @date 2022/2/15 15:47
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public CaptchaServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String create(String type) {
        String key = IdUtil.fastSimpleUUID();
        AbstractCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100);
        stringRedisTemplate.opsForValue().set(type + captcha.getCode(), "1", 5, TimeUnit.MINUTES);
        return captcha.getImageBase64();
    }
}
