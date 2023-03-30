package com.rzyc.fulongapi.bean.fire.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author by jilin
 * @Classname DeleteAlarmDto
 * @Description TODO
 * @Date 2022/5/17 17:07
 */
@ApiModel("删除出警信息请求实体")
public class DeleteAlarmDto {
    @ApiModelProperty("出警id")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
