package com.fanxb.backend.service;

/**
 * 验证码相关类
 *
 * @author fanxb
 * @date 2022/2/15 15:27
 */
public interface CaptchaService {

    /***
     * 返回验证码base64
     *
     * @param type 验证码类别
     *  @return {@link String}
     * @author fanxb
     * date 2022/2/15 16:04
     */
    String create(String type);
}
