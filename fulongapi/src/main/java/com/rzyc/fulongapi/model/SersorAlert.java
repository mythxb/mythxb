package com.rzyc.fulongapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 传感器报警信息
 * </p>
 *
 * @author
 * @since 2023-05-27
 */
@TableName("sersor_alert")
@ApiModel(value="SersorAlert对象", description="传感器报警信息")
public class SersorAlert implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报警记录")
    @TableId("alert_id")
    private String alertId;

    @ApiModelProperty(value = "传感器id")
    @TableField("sersor_id")
    private String sersorId;

    @ApiModelProperty(value = "报警时间")
    @TableField("alert_time")
    private Date alertTime;

    @ApiModelProperty(value = "状态 1、未处理 2、处理中 3、已处理")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "报警值")
    @TableField("current_val")
    private String currentVal;

    @ApiModelProperty(value = "报警状态 1、正常 2、低压报警 3、高压报警")
    @TableField("alert_state")
    private Integer alertState;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    @TableField("modify_by")
    private String modifyBy;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private Date modifyTime;

    @ApiModelProperty(value = "传感器位置")
    @TableField(exist = false)
    private String address;

    @ApiModelProperty(value = "传感器类型 1、地面消防栓 2、壁挂消防栓")
    @TableField(exist = false)
    private String sersorType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSersorType() {
        return sersorType;
    }

    public void setSersorType(String sersorType) {
        this.sersorType = sersorType;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }
    public String getSersorId() {
        return sersorId;
    }

    public void setSersorId(String sersorId) {
        this.sersorId = sersorId;
    }
    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }
    public Integer getAlertState() {
        return alertState;
    }

    public void setAlertState(Integer alertState) {
        this.alertState = alertState;
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
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "SersorAlert{" +
            "alertId=" + alertId +
            ", sersorId=" + sersorId +
            ", alertTime=" + alertTime +
            ", state=" + state +
            ", currentVal=" + currentVal +
            ", alertState=" + alertState +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
