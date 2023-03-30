package com.rzyc.fulongapi.bean.build;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class BulidPageDto {


    @ApiModelProperty(value = "关键字")
    private String condition;

    @ApiModelProperty("1.西街 2.东街")
    private Integer direction;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码",required = true,example = "1")
    private Integer page;//当前页

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数",required = true,example = "10")
    private Integer pageSize;//每页显示多少条

    @ApiModelProperty("用户id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
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
