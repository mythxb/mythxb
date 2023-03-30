package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("消防隐患整改统计")
public class DangerNumDto {

    @ApiModelProperty("单元id")
    private String unitId;

    @ApiModelProperty("隐患类型 1.燃气 3.消防")
    private Integer dangerType;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getDangerType() {
        return dangerType;
    }

    public void setDangerType(Integer dangerType) {
        this.dangerType = dangerType;
    }
}
