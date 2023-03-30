package com.rzyc.fulongapi.model.statistic;

import java.math.BigDecimal;

public class DangerStatistic {

    //整改已完成百分比
    private Double rectifiedPercent = 0.0;

    //未整改百分比
    private Double notRectifiedPercent = 0.0;

    //整改中百分比
    private Double rectifyingPercent = 0.0;

    //隐患总数
    private Integer dangerTotal = 0;

    //整改完成总数
    private Integer rectifiedTotal = 0;

    //未整改总数
    private Integer notRectifiedTotal = 0;

    //整改中总数
    private Integer rectifyingTotal = 0;

    //无法整改总数
    private Integer unableToRectify = 0;

    public Double getRectifiedPercent() {
        return rectifiedPercent;
    }

    public void setRectifiedPercent(Double rectifiedPercent) {
        this.rectifiedPercent = rectifiedPercent;
    }

    public Double getNotRectifiedPercent() {
        return notRectifiedPercent;
    }

    public void setNotRectifiedPercent(Double notRectifiedPercent) {
        this.notRectifiedPercent = notRectifiedPercent;
    }

    public Double getRectifyingPercent() {
        return rectifyingPercent;
    }

    public void setRectifyingPercent(Double rectifyingPercent) {
        this.rectifyingPercent = rectifyingPercent;
    }

    public Integer getDangerTotal() {
        return dangerTotal;
    }

    public void setDangerTotal(Integer dangerTotal) {
        this.dangerTotal = dangerTotal;
    }

    public Integer getRectifiedTotal() {
        return rectifiedTotal;
    }

    public void setRectifiedTotal(Integer rectifiedTotal) {
        this.rectifiedTotal = rectifiedTotal;
    }

    public Integer getNotRectifiedTotal() {
        return notRectifiedTotal;
    }

    public void setNotRectifiedTotal(Integer notRectifiedTotal) {
        this.notRectifiedTotal = notRectifiedTotal;
    }

    public Integer getRectifyingTotal() {
        return rectifyingTotal;
    }

    public void setRectifyingTotal(Integer rectifyingTotal) {
        this.rectifyingTotal = rectifyingTotal;
    }

    public Integer getUnableToRectify() {
        return unableToRectify;
    }

    public void setUnableToRectify(Integer unableToRectify) {
        this.unableToRectify = unableToRectify;
    }
}
