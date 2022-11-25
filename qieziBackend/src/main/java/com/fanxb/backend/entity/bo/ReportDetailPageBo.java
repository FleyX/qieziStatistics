package com.fanxb.backend.entity.bo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fanxb.backend.entity.po.DetailPagePo;
import com.fanxb.backend.entity.po.HostDayPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ReportDetailPageBo {
    @ExcelProperty(value = "页面路径")
    private String path;
    @ExcelProperty(value = "PV")
    private long pv;
    @ExcelProperty(value = "UV")
    private long uv;

    public ReportDetailPageBo(DetailPagePo po) {
        BeanUtil.copyProperties(po, this);
    }
}
