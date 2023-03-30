package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-17
 */
@ApiModel(value="Building对象", description="")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String buildId;

    @ApiModelProperty(value = "建筑体名称")
    private String buildName;

    private String buildingTime;

    @ApiModelProperty("楼栋编号")
    private Integer buildNumber;

    private String buildLat;

    private String buildLon;

    private String gridParentId;

    private String streetParentId;

    @ApiModelProperty("楼栋管理员")
    private String buildingManager;

    @ApiModelProperty("联系电话")
    private String buildingManagerContact;

    @ApiModelProperty(value = "风险等级 红：4 橙：3 黄：2 蓝：1")
    private Integer riskLevel;

    @ApiModelProperty("二维码")
    private String qrCode;

    @ApiModelProperty("大屏单元隐藏标志")
    private Integer mapUnitHiddenState;

    private String createBy;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String modifyBy;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty("1.西街 2.东街")
    private Integer direction;

    @TableField(exist = false)
    private List<BuildUnit> units;

    //单元数
    @TableField(exist = false)
    private Integer unitCount;

    //楼栋户数
    @TableField(exist = false)
    private Integer householdSize;

    public Integer getMapUnitHiddenState() {
        return mapUnitHiddenState;
    }

    public void setMapUnitHiddenState(Integer mapUnitHiddenState) {
        this.mapUnitHiddenState = mapUnitHiddenState;
    }

    public String getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(String buildingManager) {
        this.buildingManager = buildingManager;
    }

    public String getBuildingManagerContact() {
        return buildingManagerContact;
    }

    public void setBuildingManagerContact(String buildingManagerContact) {
        this.buildingManagerContact = buildingManagerContact;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(Integer householdSize) {
        this.householdSize = householdSize;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public String getBuildingTime() {
        return buildingTime;
    }

    public void setBuildingTime(String buildingTime) {
        this.buildingTime = buildingTime;
    }

    public List<BuildUnit> getUnits() {
        return units;
    }

    public void setUnits(List<BuildUnit> units) {
        this.units = units;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
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
    public String getBuildLat() {
        return buildLat;
    }

    public void setBuildLat(String buildLat) {
        this.buildLat = buildLat;
    }
    public String getBuildLon() {
        return buildLon;
    }

    public void setBuildLon(String buildLon) {
        this.buildLon = buildLon;
    }
    public String getGridParentId() {
        return gridParentId;
    }

    public void setGridParentId(String gridParentId) {
        this.gridParentId = gridParentId;
    }
    public String getStreetParentId() {
        return streetParentId;
    }

    public void setStreetParentId(String streetParentId) {
        this.streetParentId = streetParentId;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return "Building{" +
            "buildId=" + buildId +
            ", buildName=" + buildName +
            ", buildNumber=" + buildNumber +
            ", buildLat=" + buildLat +
            ", buildLon=" + buildLon +
            ", gridParentId=" + gridParentId +
            ", streetParentId=" + streetParentId +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }


}
