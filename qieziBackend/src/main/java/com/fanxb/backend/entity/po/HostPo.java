package com.fanxb.backend.entity.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostPo {
    private int id;
    /**
     * 标识key
     */
    private String key;
    /**
     * 密钥
     */
    private String secret;
    /**
     * 网站名
     */
    private String name;
    /**
     * 网站域名
     */
    private String host;
    private long pv;
    private long uv;
}
