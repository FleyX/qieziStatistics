package com.fanxb.backend.controller;

import com.fanxb.backend.constants.RedisConstant;
import com.fanxb.backend.entity.ResultObject;
import com.fanxb.backend.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 *
 * @author fanxb
 * @date 2022/2/15 16:01
 */
@RestController()
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    @Autowired
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 注册用验证码
     */
    @GetMapping("/sign")
    public ResultObject signCaptcha() {
        return ResultObject.success(captchaService.create(RedisConstant.APPLICATION_SIGN_PRE));
    }
}
