package com.fanxb.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "key不能为空")
    private String key;
    @NotBlank(message = "secret不能为空")
    private String secret;
}
