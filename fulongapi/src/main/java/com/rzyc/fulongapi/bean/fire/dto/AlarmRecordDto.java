package com.rzyc.fulongapi.bean.fire.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author by jilin
 * @Classname AlramRecordDto
 * @Description TODO
 * @Date 2022/5/17 14:48
 */
@ApiModel("出警记录")
public class AlarmRecordDto {



    @ApiModelProperty(value = "每页大小",required = true)
    @Range(min = 1,max = 100,message = "每页大小为1-100")
    private Integer pageSize;

    @ApiModelProperty(value = "页码",required = true)
    @Range(min = 1,message = "页码必须为非负整数")
    private Integer page;

    @ApiModelProperty(value = "消防站id")
    private String fireStationId;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getFireStationId() {
        return fireStationId;
    }

    public void setFireStationId(String fireStationId) {
        this.fireStationId = fireStationId;
    }
}
