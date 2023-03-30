package com.rzyc.fulongapi.bean.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("人口信息")
public class PopulationInfo {

    @ApiModelProperty("总人口")
    private Integer total;

    @ApiModelProperty("本地人口")
    private Integer localNum;

    @ApiModelProperty("外地人口")
    private Integer outTownNum;

    @ApiModelProperty("特殊人口")
    private Integer specialNum;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLocalNum() {
        return localNum;
    }

    public void setLocalNum(Integer localNum) {
        this.localNum = localNum;
    }

    public Integer getOutTownNum() {
        return outTownNum;
    }

    public void setOutTownNum(Integer outTownNum) {
        this.outTownNum = outTownNum;
    }

    public Integer getSpecialNum() {
        return specialNum;
    }

    public void setSpecialNum(Integer specialNum) {
        this.specialNum = specialNum;
    }
}
