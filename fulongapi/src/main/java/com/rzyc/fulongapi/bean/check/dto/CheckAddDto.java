package com.rzyc.fulongapi.bean.check.dto;

import com.rzyc.fulongapi.enums.CheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("新增检查")
public class CheckAddDto {

    @ApiModelProperty("检查id")
    private String checkId;

    @NotNull(message = "用户不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;

    @NotNull(message = "楼层不能为空")
    @ApiModelProperty(value = "楼层id",required = true)
    private String floorId;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("整改期限 yyyy-MM-dd")
    private String rectifyTerm;

    @ApiModelProperty("检查类型：1、入户检查 2、公共区域检查 3、企业检查")
    private Integer checkType = CheckType.HOUSE.getType();

    @ApiModelProperty(value = "楼层id",required = true)
    private List<CheckInfoDto> checkInfos;

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getRectifyTerm() {
        return rectifyTerm;
    }

    public void setRectifyTerm(String rectifyTerm) {
        this.rectifyTerm = rectifyTerm;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public List<CheckInfoDto> getCheckInfos() {
        return checkInfos;
    }

    public void setCheckInfos(List<CheckInfoDto> checkInfos) {
        this.checkInfos = checkInfos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
