package com.fanxb.backend.entity.dto;

import lombok.Data;

/**
 * 注册
 *
 * @author fanxb
 * @date 2022/2/15 16:29
 */
@Data
public class ApplicationSignDto {
    /**
     * 验证码code
     */
    private String code;
    /**
     * 网站名
     */
    private String name;
    /**
     * 域名
     */
    private String host;
}
