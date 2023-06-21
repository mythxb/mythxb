package com.rzyc.fulongapi.bean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dong
 * @date 2023-06-19 11:10
 * @Version V1.0
 */
@ApiModel("token")
public class UserTokenDto {

    @ApiModelProperty("用户token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
