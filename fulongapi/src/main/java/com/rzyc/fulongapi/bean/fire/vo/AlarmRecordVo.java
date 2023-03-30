package com.rzyc.fulongapi.bean.fire.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author by jilin
 * @Classname AlramRecordVo
 * @Description TODO
 * @Date 2022/5/17 14:38
 */
@ApiModel("出警记录返回实体")
public class AlarmRecordVo {

    @ApiModelProperty("出警记录id")
    private String recordId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("出警时间")
    private Date alarmTime;

    @ApiModelProperty("出警人员")
    private String alarmMember;

    @ApiModelProperty("出警地址")
    private String alarmAddress;

    @ApiModelProperty("出警消防车辆")
    private String alarmCar;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("起火原因")
    private String fireCause;

    @ApiModelProperty("损失")
    private String loss;

    @ApiModelProperty("消防站id")
    private String fireStationId;

    public String getFireStationId() {
        return fireStationId;
    }

    public void setFireStationId(String fireStationId) {
        this.fireStationId = fireStationId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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
}
