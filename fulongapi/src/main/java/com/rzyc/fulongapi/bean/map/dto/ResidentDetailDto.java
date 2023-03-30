package com.rzyc.fulongapi.bean.map.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("小区住房信息分页")
public class ResidentDetailDto {
    @ApiModelProperty(value = "关键字")
    private String condition;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer page;

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数", required = true, example = "10")
    private Integer pageSize;

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
