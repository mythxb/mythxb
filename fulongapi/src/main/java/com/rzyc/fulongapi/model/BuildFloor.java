package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-18
 */
@ApiModel(value="BuildFloor对象", description="")
public class BuildFloor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("楼层id")
    private String floorId;

    @ApiModelProperty(value = "建筑id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String unitId;

    @ApiModelProperty(value = "单元号")
    private Integer unitNumber;

    @ApiModelProperty(value = "楼层号")
    private Integer floorNumber;

    @ApiModelProperty(value = "户数")
    private Integer householdSize;

    @ApiModelProperty(value = "户数")
    private Integer houseType;

    @ApiModelProperty(value = "居住人数")
    private Integer personNum;

    @ApiModelProperty(value = "燃气灶具数")
    private Integer cookerNum;

    @ApiModelProperty(value = "燃气户表数")
    private Integer usertableNum;

    @ApiModelProperty(value = "热水器数")
    private Integer heaterNum;

    @ApiModelProperty(value = "风险等级 红：4 橙：3 黄：2 蓝：1")
    private Integer riskLevel;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    //未整改数
    @TableField(exist = false)
    private long notRectifiedTotal;

    //整改中数
    @TableField(exist = false)
    private long rectifyingTotal;

    //已整改数
    @TableField(exist = false)
    private long rectifiedTotal;

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public long getNotRectifiedTotal() {
        return notRectifiedTotal;
    }

    public void setNotRectifiedTotal(long notRectifiedTotal) {
        this.notRectifiedTotal = notRectifiedTotal;
    }

    public long getRectifyingTotal() {
        return rectifyingTotal;
    }

    public void setRectifyingTotal(long rectifyingTotal) {
        this.rectifyingTotal = rectifyingTotal;
    }

    public long getRectifiedTotal() {
        return rectifiedTotal;
    }

    public void setRectifiedTotal(long rectifiedTotal) {
        this.rectifiedTotal = rectifiedTotal;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getCookerNum() {
        return cookerNum;
    }

    public void setCookerNum(Integer cookerNum) {
        this.cookerNum = cookerNum;
    }

    public Integer getUsertableNum() {
        return usertableNum;
    }

    public void setUsertableNum(Integer usertableNum) {
        this.usertableNum = usertableNum;
    }

    public Integer getHeaterNum() {
        return heaterNum;
    }

    public void setHeaterNum(Integer heaterNum) {
        this.heaterNum = heaterNum;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }
    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }
    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }
    public Integer getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(Integer householdSize) {
        this.householdSize = householdSize;
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
        return "BuildFloor{" +
            "floorId=" + floorId +
            ", buildingId=" + buildingId +
            ", unitId=" + unitId +
            ", unitNumber=" + unitNumber +
            ", floorNumber=" + floorNumber +
            ", householdSize=" + householdSize +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
