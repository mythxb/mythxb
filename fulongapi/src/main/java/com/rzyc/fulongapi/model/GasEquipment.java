package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-03-10
 */
@ApiModel(value="GasEquipment对象", description="")
public class GasEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String geId;

    @ApiModelProperty(value = "器材名称")
    private String gasEqName;

    @ApiModelProperty(value = "归属类型")
    private String eqType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "器材购买时间")
    private Date gasEqPurchaseTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "器材出厂时间")
    private Date gasEqDeliveryTime;

    @ApiModelProperty(value = "1.正常 2.超期")
    private Integer overdue;

    @ApiModelProperty(value = "楼层外键")
    private String floorId;

    private String unitId;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    @TableField(exist = false)
    private String buildingId;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getGeId() {
        return geId;
    }

    public void setGeId(String geId) {
        this.geId = geId;
    }
    public String getGasEqName() {
        return gasEqName;
    }

    public void setGasEqName(String gasEqName) {
        this.gasEqName = gasEqName;
    }
    public String getEqType() {
        return eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }
    public Date getGasEqPurchaseTime() {
        return gasEqPurchaseTime;
    }

    public void setGasEqPurchaseTime(Date gasEqPurchaseTime) {
        this.gasEqPurchaseTime = gasEqPurchaseTime;
    }
    public Date getGasEqDeliveryTime() {
        return gasEqDeliveryTime;
    }

    public void setGasEqDeliveryTime(Date gasEqDeliveryTime) {
        this.gasEqDeliveryTime = gasEqDeliveryTime;
    }
    public Integer getOverdue() {
        return overdue;
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
        return "GasEquipment{" +
            "geId=" + geId +
            ", gasEqName=" + gasEqName +
            ", eqType=" + eqType +
            ", gasEqPurchaseTime=" + gasEqPurchaseTime +
            ", gasEqDeliveryTime=" + gasEqDeliveryTime +
            ", overdue=" + overdue +
            ", floorId=" + floorId +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
