package com.rzyc.fulongapi.bean.fire.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author by jilin
 * @Classname AlarmAddOrUpdateDto
 * @Description TODO
 * @Date 2022/5/17 16:27
 */
@ApiModel("出警信息新增或修改实体")
public class AlarmAddOrUpdateDto {
    @ApiModelProperty(value = "出警id",required = true)
    @NotNull(message = "出警id不能为空")
    private String recordId;

    @ApiModelProperty(value = "出警时间",required = true)
    @NotNull(message = "出警时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String alarmTime;

    @ApiModelProperty(value = "出警人员",required = true)
    @NotBlank(message = "出警人员不能为空")
    private String alarmMember;

    @ApiModelProperty(value = "出警地址",required = true)
    @NotBlank(message = "出警地址不能为空")
    private String alarmAddress;

    @ApiModelProperty(value = "出警消防车辆",required = true)
    @NotBlank(message = "出警消防车辆不能为空")
    private String alarmCar;

    @ApiModelProperty(value = "结束时间",required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private String endTime;


    @ApiModelProperty(value = "起火原因",required = true)
    @NotBlank(message = "起火原因不能为空")
    private String fireCause;


    @ApiModelProperty(value = "人身财产和生命安全损失情况",required = true)
    @NotBlank(message = "人身财产和生命安全损失情况不能为空")
    private String loss;

    @ApiModelProperty(value = "消防站id",required = true)
    @NotBlank(message = "消防站id不能为空")
    private String fireStationId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmMember() {
        return alarmMember;
    }

    public void setAlarmMember(String alarmMember) {
        this.alarmMember = alarmMember;
    }

    public String getAlarmAddress() {
        return alarmAddress;
    }

    public void setAlarmAddress(String alarmAddress) {
        this.alarmAddress = alarmAddress;
    }

    public String getAlarmCar() {
        return alarmCar;
    }

    public void setAlarmCar(String alarmCar) {
        this.alarmCar = alarmCar;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFireCause() {
        return fireCause;
    }

    public void setFireCause(String fireCause) {
        this.fireCause = fireCause;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getFireStationId() {
        return fireStationId;
    }

    public void setFireStationId(String fireStationId) {
        this.fireStationId = fireStationId;
    }
}
