package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("隐患数据")
public class DangerDataDto {


    @ApiModelProperty(value = "关键字")
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
