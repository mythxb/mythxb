package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import javax.validation.constraints.NotNull;

@ApiModel("关键字列表")
public class DangerPageDto {

    @ApiModelProperty(value = "关键字")
    private String condition;

    @ApiModelProperty(value = "楼层id、楼栋id、企业id")
    private String targetId;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd")
    private String startTime;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd")
    private String endTime;

    @ApiModelProperty(value = "状态 1.未整改 2.整改中 3.已整改")
    private Integer state;

    @ApiModelProperty("隐患级别 1.一般隐患 2.较大隐患")
    private Integer riskLevel;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码",required = true,example = "1")
    private Integer page;//当前页

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数",required = true,example = "10")
    private Integer pageSize;//每页显示多少条

    @ApiModelProperty("单元id")
    private String unitId;

    @ApiModelProperty("隐患类型 1.燃气 3.消防")
    private Integer dangerType;

    public Integer getDangerType() {
        return dangerType;
    }

    public void setDangerType(Integer dangerType) {
        this.dangerType = dangerType;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @ApiModelProperty(value = "用户id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
