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
 * 传感器信息
 * </p>
 *
 * @author 
 * @since 2023-05-27
 */
@TableName("sersor")
@ApiModel(value="Sersor对象", description="传感器信息")
public class Sersor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "传感器id")
    @TableId("sersor_id")
    private String sersorId;

    @ApiModelProperty(value = "传感器编号")
    @TableField("sersor_num")
    private String sersorNum;

    @ApiModelProperty(value = "传感器位置")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "当前值")
    @TableField("current_val")
    private String currentVal;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "单位")
    @TableField("sersor_unit")
    private String sersorUnit;

    @ApiModelProperty(value = "传感器类型 1、地面消防栓 2、壁挂消防栓")
    @TableField("sersor_type")
    private Integer sersorType;

    @ApiModelProperty(value = "报警状态 1、正常 2、报警")
    @TableField("alert_state")
    private Integer alertState;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "更新人")
    @TableField("modify_by")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("modify_time")
    private Date modifyTime;

    public String getSersorId() {
        return sersorId;
    }

    public void setSersorId(String sersorId) {
        this.sersorId = sersorId;
    }
    public String getSersorNum() {
        return sersorNum;
    }

    public void setSersorNum(String sersorNum) {
        this.sersorNum = sersorNum;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getSersorUnit() {
        return sersorUnit;
    }

    public void setSersorUnit(String sersorUnit) {
        this.sersorUnit = sersorUnit;
    }
    public Integer getSersorType() {
        return sersorType;
    }

    public void setSersorType(Integer sersorType) {
        this.sersorType = sersorType;
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
        return "Sersor{" +
            "sersorId=" + sersorId +
            ", sersorNum=" + sersorNum +
            ", address=" + address +
            ", currentVal=" + currentVal +
            ", updateTime=" + updateTime +
            ", sersorUnit=" + sersorUnit +
            ", sersorType=" + sersorType +
            ", alertState=" + alertState +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
