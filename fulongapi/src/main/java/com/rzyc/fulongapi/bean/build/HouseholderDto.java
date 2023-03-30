package com.rzyc.fulongapi.bean.build;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("其他管理员")
public class HouseholderDto {

    @ApiModelProperty("管理员")
    private String householderName;

    @ApiModelProperty("管理员电话")
    private String householderPhone;

    public String getHouseholderName() {
        return householderName;
    }

    public void setHouseholderName(String householderName) {
        this.householderName = householderName;
    }

    public String getHouseholderPhone() {
        return householderPhone;
    }

    public void setHouseholderPhone(String householderPhone) {
        this.householderPhone = householderPhone;
    }
}
