package com.rzyc.fulongapi.bean.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("社区统计")
public class CommunityInfo {

    @ApiModelProperty("面积")
    private Double areaNum;

    @ApiModelProperty("楼栋数")
    private Integer buildNum;

    @ApiModelProperty("单元数")
    private Integer unitNum;

    @ApiModelProperty("户数")
    private Integer houseNum;

    public Double getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(Double areaNum) {
        this.areaNum = areaNum;
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    public Integer getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }
}
