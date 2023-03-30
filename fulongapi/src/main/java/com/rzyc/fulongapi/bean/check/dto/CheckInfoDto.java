package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("检查信息")
public class CheckInfoDto {

    @ApiModelProperty(value = "检查项id")
    private String itemId;

    @ApiModelProperty("检查结果 1.合格 2.不合格 3：不涉及 4：未选择")
    private Integer checkResult;

    @ApiModelProperty("隐患信息")
    private List<DangerInfoDto> dangerInfos;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public List<DangerInfoDto> getDangerInfos() {
        return dangerInfos;
    }

    public void setDangerInfos(List<DangerInfoDto> dangerInfos) {
        this.dangerInfos = dangerInfos;
    }
}
