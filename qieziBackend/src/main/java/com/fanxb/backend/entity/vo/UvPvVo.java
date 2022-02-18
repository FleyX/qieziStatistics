package com.fanxb.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * uv,pv数据
 *
 * @author fanxb
 * @date 2022/2/16 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UvPvVo {
    private long totalUv;
    private long totalPv;
    private long pageUv;
    private long pagePv;
}
