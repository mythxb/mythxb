package com.rzyc.fulongapi.bean.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("PC登录")
public class PcLoginDto {


    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String sysusername;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String syspassword;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String generateCode;

    public String getSysusername() {
        return sysusername;
    }

    public void setSysusername(String sysusername) {
        this.sysusername = sysusername;
    }

    public String getSyspassword() {
        return syspassword;
    }

    public void setSyspassword(String syspassword) {
        this.syspassword = syspassword;
    }

    public String getGenerateCode() {
        return generateCode;
    }

    public void setGenerateCode(String generateCode) {
        this.generateCode = generateCode;
    }
}
