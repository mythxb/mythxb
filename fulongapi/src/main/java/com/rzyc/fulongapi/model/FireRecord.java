package com.rzyc.fulongapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 火灾出警记录
 * </p>
 *
 * @author 
 * @since 2022-05-17
 */
@TableName("fire_record")
@ApiModel(value="FireRecord对象", description="火灾出警记录")
public class FireRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出警记录id")
    @TableId("record_id")
    private String recordId;

    @ApiModelProperty(value = "出警时间")
    @TableField("alarm_time")
    private Date alarmTime;

    @ApiModelProperty(value = "出警人员")
    @TableField("alarm_member")
    private String alarmMember;

    @ApiModelProperty(value = "出警地址")
    @TableField("alarm_address")
    private String alarmAddress;

    @ApiModelProperty(value = "出警消防车辆")
    @TableField("alarm_car")
    private String alarmCar;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "起火原因")
    @TableField("fire_cause")
    private String fireCause;

    @ApiModelProperty(value = "人身财产和生命安全损失情况")
    @TableField("loss")
    private String loss;

    @ApiModelProperty(value = "消防站id")
    @TableField("fire_station_id")
    private String fireStationId;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("modify_by")
    private String modifyBy;

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
    public String getFireStationId() {
        return fireStationId;
    }

    public void setFireStationId(String fireStationId) {
        this.fireStationId = fireStationId;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Override
    public String toString() {
        return "FireRecord{" +
            "recordId=" + recordId +
            ", alarmTime=" + alarmTime +
            ", alarmMember=" + alarmMember +
            ", alarmAddress=" + alarmAddress +
            ", alarmCar=" + alarmCar +
            ", endTime=" + endTime +
            ", fireCause=" + fireCause +
            ", loss=" + loss +
            ", fireStationId=" + fireStationId +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
