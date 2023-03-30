package com.rzyc.fulongapi.bean.check.dto;

import com.common.utils.model.SingleResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("隐患信息")
public class DangerInfoDto {

    @ApiModelProperty("隐患id")
    private String dangerId;

    @ApiModelProperty("隐患描述")
    private String dangerDesc;

    @ApiModelProperty("隐患级别 1.一般隐患 2.重大隐患")
    private Integer normalorimportant;

    public String getDangerDesc() {
        return dangerDesc;
    }

    public void setDangerDesc(String dangerDesc) {
        this.dangerDesc = dangerDesc;
    }

    public Integer getNormalorimportant() {
        return normalorimportant;
    }

    public void setNormalorimportant(Integer normalorimportant) {
        this.normalorimportant = normalorimportant;
    }

    public String getDangerId() {
        return dangerId;
    }

    public void setDangerId(String dangerId) {
        this.dangerId = dangerId;
    }
}
