package com.rzyc.fulongapi.bean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("修改密码")
public class ChangePasswdDto {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;

    @NotNull(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码",required = true)
    private String nowPasswd;

    @NotNull(message = "原密码不能为空")
    @ApiModelProperty(value = "原密码",required = true)
    private String oldPasswd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNowPasswd() {
        return nowPasswd;
    }

    public void setNowPasswd(String nowPasswd) {
        this.nowPasswd = nowPasswd;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }
}
