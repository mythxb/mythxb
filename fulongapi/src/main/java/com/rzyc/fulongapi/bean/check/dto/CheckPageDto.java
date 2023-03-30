package com.rzyc.fulongapi.bean.check.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("检查列表")
public class CheckPageDto {


    @ApiModelProperty(value = "关键字")
    private String condition;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd")
    private String startTime;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd")
    private String endTime;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码",required = true,example = "1")
    private Integer page;//当前页

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数",required = true,example = "10")
    private Integer pageSize;//每页显示多少条

    @ApiModelProperty(value = "用户id")
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
