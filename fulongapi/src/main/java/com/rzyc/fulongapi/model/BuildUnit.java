package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @since 2022-02-17
 */
@ApiModel(value="BuildUnit对象", description="")
public class BuildUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("单位id")
    @TableId
    private String unitId;

    @ApiModelProperty(value = "管理员名称")
    private String unitManager;

    @ApiModelProperty(value = "管理员电话")
    private String unitManagerContact;

    @ApiModelProperty(value = "建筑物id")
    private String buildingId;

    @ApiModelProperty(value = "单元号")
    private Integer unitNumber;

    @ApiModelProperty(value = "单元名称")
    private String unitName;

    @ApiModelProperty(value = "楼层数")
    private Integer floorCount;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @ApiModelProperty(value = "风险等级 红：4 橙：3 黄：2 蓝：1")
    private Integer riskLevel;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String createBy;

    private String modifyBy;

    @TableField(exist = false)
    private Integer personNum;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @TableField(exist = false)
    @ApiModelProperty("楼栋名")
    private String buildName;

    @TableField(exist = false)
    @ApiModelProperty("楼栋号")
    private Integer buildNumber;

    //灶具数
    @TableField(exist = false)
    private Integer cookerNum;

    //热水器数
    @TableField(exist = false)
    private Integer heaterNum;

    @TableField(exist = false)
    private CheckDanger checkDanger;

    //隐患数量
    @ApiModelProperty("未整改隐患数量")
    @TableField(exist = false)
    private Integer dangerNum;

    @ApiModelProperty("隐患描述")
    @TableField(exist = false)
    private String dangerInfo;

    @TableField(exist = false)
    private List<BuildUnitHouseholder> householders;

    public String getDangerInfo() {
        return dangerInfo;
    }

    public void setDangerInfo(String dangerInfo) {
        this.dangerInfo = dangerInfo;
    }

    public Integer getDangerNum() {
        return dangerNum;
    }

    public void setDangerNum(Integer dangerNum) {
        this.dangerNum = dangerNum;
    }

    public List<BuildUnitHouseholder> getHouseholders() {
        return householders;
    }

    public void setHouseholders(List<BuildUnitHouseholder> householders) {
        this.householders = householders;
    }

    public Integer getCookerNum() {
        return cookerNum;
    }

    public void setCookerNum(Integer cookerNum) {
        this.cookerNum = cookerNum;
    }

    public Integer getHeaterNum() {
        return heaterNum;
    }

    public void setHeaterNum(Integer heaterNum) {
        this.heaterNum = heaterNum;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public CheckDanger getCheckDanger() {
        return checkDanger;
    }

    public void setCheckDanger(CheckDanger checkDanger) {
        this.checkDanger = checkDanger;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public String getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(String unitManager) {
        this.unitManager = unitManager;
    }
    public String getUnitManagerContact() {
        return unitManagerContact;
    }

    public void setUnitManagerContact(String unitManagerContact) {
        this.unitManagerContact = unitManagerContact;
    }
    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
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
        return "BuildUnit{" +
            "unitId=" + unitId +
            ", unitManager=" + unitManager +
            ", unitManagerContact=" + unitManagerContact +
            ", buildingId=" + buildingId +
            ", unitNumber=" + unitNumber +
            ", unitName=" + unitName +
            ", floorCount=" + floorCount +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
