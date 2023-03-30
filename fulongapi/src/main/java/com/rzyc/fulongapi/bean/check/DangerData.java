package com.rzyc.fulongapi.bean.check;

import com.rzyc.fulongapi.model.DangerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("隐患统计信息")
public class DangerData {

    @ApiModelProperty("隐患总数")
    private Integer totalNum = 0;

    @ApiModelProperty("已整改隐患")
    private Integer rectifyNum = 0;

    @ApiModelProperty("不能整改隐患")
    private Integer unableRectify = 0;

    @ApiModelProperty("整改中")
    private Integer rectifyingNum = 0;

    @ApiModelProperty("未整改数")
    private Integer notRectifyNum = 0;

    @ApiModelProperty("整改完成率")
    private Integer rectifyRate = 0;

    @ApiModelProperty("其他三类统计")
    private List<DangerType> dangerNumInfo;

    public Integer getRectifyRate() {
        return rectifyRate;
    }

    public void setRectifyRate(Integer rectifyRate) {
        this.rectifyRate = rectifyRate;
    }

    public List<DangerType> getDangerNumInfo() {
        return dangerNumInfo;
    }

    public void setDangerNumInfo(List<DangerType> dangerNumInfo) {
        this.dangerNumInfo = dangerNumInfo;
    }

    public Integer getNotRectifyNum() {
        return notRectifyNum;
    }

    public void setNotRectifyNum(Integer notRectifyNum) {
        this.notRectifyNum = notRectifyNum;
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

    public Integer getUnableRectify() {
        return unableRectify;
    }

    public void setUnableRectify(Integer unableRectify) {
        this.unableRectify = unableRectify;
    }

    public Integer getRectifyingNum() {
        return rectifyingNum;
    }

    public void setRectifyingNum(Integer rectifyingNum) {
        this.rectifyingNum = rectifyingNum;
    }
}
