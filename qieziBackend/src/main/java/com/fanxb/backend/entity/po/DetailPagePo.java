package com.fanxb.backend.entity.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 页面路径详情
 *
 * @author fanxb
 * date 2022/2/15 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class DetailPagePo {
    /**
     * id
     */
    private Integer id;
    /**
     * 域名
     */
    private int hostId;
    /**
     * 页面路径
     */
    private String path;
    private long pv;
    private long uv;
}
