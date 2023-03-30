package com.rzyc.fulongapi.bean.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("楼栋隐患数")
public class BuildDangerNum {


    @ApiModelProperty("楼栋id")
    private String buildId;

    @ApiModelProperty("楼栋名")
    private String buildName;

    @ApiModelProperty("楼栋数据")
    private Integer dangerNum;

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Integer getDangerNum() {
        return dangerNum;
    }

    public void setDangerNum(Integer dangerNum) {
        this.dangerNum = dangerNum;
    }
}
