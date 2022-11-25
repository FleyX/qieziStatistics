package com.fanxb.backend.entity.bo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fanxb.backend.entity.po.HostDayPo;
import com.fanxb.backend.entity.po.HostPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ReportHostDayBo {
    @ExcelProperty(value = "日期")
    private String date;
    @ExcelProperty(value = "PV")
    private long pv;
    @ExcelProperty(value = "UV")
    private long uv;

    public ReportHostDayBo(HostDayPo po) {
        String temp = String.valueOf(po.getDayNum());
        this.date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6);
        this.pv = po.getPv();
        this.uv = po.getUv();
    }
}
