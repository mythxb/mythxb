package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("企业列表")
public class EntPageDto {

    @ApiModelProperty(value = "关键字")
    private String condition;

    @ApiModelProperty(value = "企业id")
    private String enterpriseId;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码",required = true,example = "1")
    private Integer page;//当前页

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数",required = true,example = "10")
    private Integer pageSize;//每页显示多少条

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
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
