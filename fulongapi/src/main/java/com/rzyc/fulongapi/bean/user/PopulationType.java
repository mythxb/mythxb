package com.rzyc.fulongapi.bean.user;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author myth
 * @date 2022年06月11日 10:30
 */
public class PopulationType {

    @ApiModelProperty("自用")
    private Integer selfUseNum;

    @ApiModelProperty("出租")
    private Integer leaseNum;

    @ApiModelProperty("商用")
    private Integer commercialNum;

    public Integer getSelfUseNum() {
        return selfUseNum;
    }

    public void setSelfUseNum(Integer selfUseNum) {
        this.selfUseNum = selfUseNum;
    }

    public Integer getLeaseNum() {
        return leaseNum;
    }

    public void setLeaseNum(Integer leaseNum) {
        this.leaseNum = leaseNum;
    }

    public Integer getCommercialNum() {
        return commercialNum;
    }

    public void setCommercialNum(Integer commercialNum) {
        this.commercialNum = commercialNum;
    }
}
