package com.fanxb.backend.entity.po;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DetailPageDayPo {

    /**
     * id
     */
    private int id;
    /**
     * hostId
     */
    private int detailPageId;
    /**
     * 日期20200202
     */
    private int dayNum;
    private long pv;
    private long uv;

}
