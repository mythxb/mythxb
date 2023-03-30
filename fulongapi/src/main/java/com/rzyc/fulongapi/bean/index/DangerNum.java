package com.rzyc.fulongapi.bean.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("折线图数据")
public class DangerNum {

    @ApiModelProperty("下标")
    private List<String> titles;

    @ApiModelProperty("折线图数据")
    private List<BrokenLine> brokenLines;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<BrokenLine> getBrokenLines() {
        return brokenLines;
    }

    public void setBrokenLines(List<BrokenLine> brokenLines) {
        this.brokenLines = brokenLines;
    }
}
