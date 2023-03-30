package com.rzyc.fulongapi.bean.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("小区住房信息类型总数")
public class ResidentTypeTotalVo {
    @ApiModelProperty("住房类型")
    private String residentType;

    @ApiModelProperty("单个住房类型的总数")
    private String housesTotal;

    public String getResidentType() {
        return residentType;
    }

    public void setResidentType(String residentType) {
        this.residentType = residentType;
    }

    public String getHousesTotal() {
        return housesTotal;
    }

    public void setHousesTotal(String housesTotal) {
        this.housesTotal = housesTotal;
    }
}
