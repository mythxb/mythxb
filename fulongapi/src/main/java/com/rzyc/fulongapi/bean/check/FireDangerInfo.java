package com.rzyc.fulongapi.bean.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("消防隐患统计")
public class FireDangerInfo {

    @ApiModelProperty("隐患总数")
    private Integer totalNum;

    @ApiModelProperty("已整改隐患")
    private Integer rectifyNum;

    @ApiModelProperty("未整改")
    private Integer notRectifyNum;

    @ApiModelProperty("整改中")
    private Integer rectifyingNum;

    @ApiModelProperty("整改完成率")
    private Double rectifyRate;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getRectifyNum() {
        return rectifyNum;
    }

    public void setRectifyNum(Integer rectifyNum) {
        this.rectifyNum = rectifyNum;
    }

    public Integer getNotRectifyNum() {
        return notRectifyNum;
    }

    public void setNotRectifyNum(Integer notRectifyNum) {
        this.notRectifyNum = notRectifyNum;
    }

    public Integer getRectifyingNum() {
        return rectifyingNum;
    }

    public void setRectifyingNum(Integer rectifyingNum) {
        this.rectifyingNum = rectifyingNum;
    }

    public Double getRectifyRate() {
        return rectifyRate;
    }

    public void setRectifyRate(Double rectifyRate) {
        this.rectifyRate = rectifyRate;
    }
}
