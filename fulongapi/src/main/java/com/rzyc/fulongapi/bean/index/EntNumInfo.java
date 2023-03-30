package com.rzyc.fulongapi.bean.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("企业数统计")
public class EntNumInfo {

    @ApiModelProperty("分类名")
    private String typeName;

    @ApiModelProperty("企业数")
    private Integer entNum;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getEntNum() {
        return entNum;
    }

    public void setEntNum(Integer entNum) {
        this.entNum = entNum;
    }
}
