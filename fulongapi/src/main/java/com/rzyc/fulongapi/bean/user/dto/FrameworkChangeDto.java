package com.rzyc.fulongapi.bean.user.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class FrameworkChangeDto {

    @ApiModelProperty(value = "架构id,传新增  不传修改")
    private String frameworkId;


    @ApiModelProperty(value = "岗位名")
    private String postName;

    @NotNull(message = "中文名不能为空")
    @ApiModelProperty(value = "中文名",required = true)
    private String chinaName;

    @NotNull(message = "电话号码不能为空")
    @ApiModelProperty(value = "电话号码",required = true)
    private String mobile;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序",required = true)
    private Integer sortId;

    public String getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(String frameworkId) {
        this.frameworkId = frameworkId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}
