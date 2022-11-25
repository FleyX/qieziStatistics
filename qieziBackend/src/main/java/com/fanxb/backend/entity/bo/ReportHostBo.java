package com.fanxb.backend.entity.bo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fanxb.backend.entity.po.HostPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ReportHostBo {
    @ExcelProperty(value = "站点名称")
    private String name;
    @ExcelProperty(value = "站点地址")
    private String host;
    @ExcelProperty(value = "PV")
    private Integer pv;
    @ExcelProperty(value = "UV")
    private Integer uv;

    public ReportHostBo(HostPo hostPo) {
        BeanUtil.copyProperties(hostPo, this);
    }
}
