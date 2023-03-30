package com.rzyc.fulongapi.bean.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("整改统计")
public class RectifyStatistics {


    @ApiModelProperty("隐患总数")
    private Integer totalNum;

    @ApiModelProperty("已整改隐患")
    private Integer rectifyNum;

    @ApiModelProperty("未整改")
    private Integer notRectifyNum;

    @ApiModelProperty("整改中")
    private Integer rectifyingNum;

    @ApiModelProperty("待整改隐患")
    private Integer stayRectifyNum;

    @ApiModelProperty("整改完成率")
    private Double rectifyRate;

    @ApiModelProperty("不能整改隐患")
    private Integer unableRectify;

    @ApiModelProperty("重大隐患数")
    private Integer majorNum;

    @ApiModelProperty("一般隐患数")
    private Integer commonlyNum;

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

    public Integer getMajorNum() {
        return majorNum;
    }

    public void setMajorNum(Integer majorNum) {
        this.majorNum = majorNum;
    }

    public Integer getCommonlyNum() {
        return commonlyNum;
    }

    public void setCommonlyNum(Integer commonlyNum) {
        this.commonlyNum = commonlyNum;
    }

    public Integer getUnableRectify() {
        return unableRectify;
    }

    public void setUnableRectify(Integer unableRectify) {
        this.unableRectify = unableRectify;
    }

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

    public Integer getStayRectifyNum() {
        return stayRectifyNum;
    }

    public void setStayRectifyNum(Integer stayRectifyNum) {
        this.stayRectifyNum = stayRectifyNum;
    }

    public Double getRectifyRate() {
        return rectifyRate;
    }

    public void setRectifyRate(Double rectifyRate) {
        this.rectifyRate = rectifyRate;
    }
}
