package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("修改隐患状态")
public class ChangeDangerDto {

    @NotNull(message = "隐患不能为空")
    @ApiModelProperty(value = "隐患id",required = true)
    private String id;

    @ApiModelProperty("整改信息")
    private String rectifyInfo;

    @NotNull(message = "隐患状态不能为空")
    @ApiModelProperty(value = "状态 1.未整改 3.已整改 4.无法整改",required = true)
    private Integer rectificationState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRectifyInfo() {
        return rectifyInfo;
    }

    public void setRectifyInfo(String rectifyInfo) {
        this.rectifyInfo = rectifyInfo;
    }

    public Integer getRectificationState() {
        return rectificationState;
    }

    public void setRectificationState(Integer rectificationState) {
        this.rectificationState = rectificationState;
    }
}
