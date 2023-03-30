package com.rzyc.fulongapi.bean.build;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 燃气点数和已整改户数
 */
@ApiModel("燃气点数和已整改户数")
public class FloorNumInfo {

    @ApiModelProperty("已整改户数")
    private Integer houseNum;

    @ApiModelProperty("燃气点位数")
    private Integer usertableNum;

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public Integer getUsertableNum() {
        return usertableNum;
    }

    public void setUsertableNum(Integer usertableNum) {
        this.usertableNum = usertableNum;
    }
}
