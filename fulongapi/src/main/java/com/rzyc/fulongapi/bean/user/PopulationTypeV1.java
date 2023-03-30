package com.rzyc.fulongapi.bean.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("人口类型信息")
public class PopulationTypeV1 {

    @ApiModelProperty("自用")
    private Integer selfUseNum;

    @ApiModelProperty("居住")
    private Integer liveNum;

    @ApiModelProperty("合租")
    private Integer sharingNum;

    @ApiModelProperty("出租")
    private Integer leaseNum;

    @ApiModelProperty("整租")
    private Integer wholeRentNum;

    @ApiModelProperty("商用")
    private Integer commercialNum;

    public Integer getSelfUseNum() {
        return selfUseNum;
    }

    public void setSelfUseNum(Integer selfUseNum) {
        this.selfUseNum = selfUseNum;
    }

    public Integer getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(Integer liveNum) {
        this.liveNum = liveNum;
    }

    public Integer getSharingNum() {
        return sharingNum;
    }

    public void setSharingNum(Integer sharingNum) {
        this.sharingNum = sharingNum;
    }

    public Integer getLeaseNum() {
        return leaseNum;
    }

    public void setLeaseNum(Integer leaseNum) {
        this.leaseNum = leaseNum;
    }

    public Integer getWholeRentNum() {
        return wholeRentNum;
    }

    public void setWholeRentNum(Integer wholeRentNum) {
        this.wholeRentNum = wholeRentNum;
    }

    public Integer getCommercialNum() {
        return commercialNum;
    }

    public void setCommercialNum(Integer commercialNum) {
        this.commercialNum = commercialNum;
    }
}
