package com.fanxb.backend.entity.po;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
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
    private int dayNum;
    private long pv;
    private long uv;

}
