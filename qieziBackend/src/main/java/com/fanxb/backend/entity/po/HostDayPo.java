package com.fanxb.backend.entity.po;


import lombok.Data;

@Data
public class HostDayPo {

    /**
     * id
     */
    private int id;
    /**
     * hostId
     */
    private int hostId;
    /**
     * 日期20200202
     */
    private int dateNum;
    private long pv;
    private long uv;

}
