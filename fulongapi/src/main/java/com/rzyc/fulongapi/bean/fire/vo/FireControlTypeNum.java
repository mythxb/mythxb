package com.rzyc.fulongapi.bean.fire.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dong
 * @date 2023-03-28 16:34
 * @Version V1.0
 */
@ApiModel("社区消防隐患类型统计")
public class FireControlTypeNum {

    @ApiModelProperty("类型名")
    private String typeName;

    @ApiModelProperty("隐患数量")
    private Integer dangerNum;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDangerNum() {
        return dangerNum;
    }

    public void setDangerNum(Integer dangerNum) {
        this.dangerNum = dangerNum;
    }
}
