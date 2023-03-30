package com.rzyc.fulongapi.bean.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("折线数据")
public class BrokenLine {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("数据")
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
