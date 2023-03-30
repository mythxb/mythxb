package com.rzyc.fulongapi.bean.map.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dong
 * @date 2023-03-29 19:18
 * @Version V1.0
 */
@ApiModel("单元隐患数量")
public class UnitDnagerNumDto {

    @ApiModelProperty("隐患类型 1.燃气 3.消防")
    private Integer dangerType;

    public Integer getDangerType() {
        return dangerType;
    }

    public void setDangerType(Integer dangerType) {
        this.dangerType = dangerType;
    }
}
