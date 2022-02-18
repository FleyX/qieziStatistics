package com.fanxb.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用注册vo
 *
 * @author fanxb
 * @date 2022/2/15 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSignVo {
    private String key;
    private String secret;
}
