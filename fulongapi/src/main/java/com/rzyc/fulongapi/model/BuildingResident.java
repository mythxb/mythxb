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
@ApiModel(value = "BuildingResident对象", description = "")
public class BuildingResident implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String residentId;

    @ApiModelProperty(value = "居民名称")
    private String residentName;

    @ApiModelProperty(value = "居民电话")
    private String residentPhone;

    @ApiModelProperty(value = "身份证")
    private String residentIdentityCard;

    @ApiModelProperty(value = "1.居住 2.商用")
    private Integer residentType;

    @ApiModelProperty(value = "居民工作单位")
    private String workPlace;

    @ApiModelProperty(value = "楼层外键")
    private String floorId;

    @ApiModelProperty(value = "单元外键")
    private String buildingUnitId;

    @ApiModelProperty(value = "状态(1:在租,2:离开)")
    private Integer status;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @TableField(exist = false)
    private String createByName;

    @TableField(exist = false)
    private String modifyByName;

    @TableField(exist = false)
    private Integer direction;

    @TableField(exist = false)
    private String buildingId;

    @TableField(exist = false)
    private String floorNumber;

    @ApiModelProperty(value = "楼栋名")
    @TableField(exist = false)
    private String buildName;

    @ApiModelProperty(value = "单元名")
    @TableField(exist = false)
    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getModifyByName() {
        return modifyByName;
    }

    public void setModifyByName(String modifyByName) {
        this.modifyByName = modifyByName;
    }

    public String getBuildingUnitId() {
        return buildingUnitId;
    }

    public void setBuildingUnitId(String buildingUnitId) {
        this.buildingUnitId = buildingUnitId;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getResidentPhone() {
        return residentPhone;
    }

    public void setResidentPhone(String residentPhone) {
        this.residentPhone = residentPhone;
    }

    public String getResidentIdentityCard() {
        return residentIdentityCard;
    }

    public void setResidentIdentityCard(String residentIdentityCard) {
        this.residentIdentityCard = residentIdentityCard;
    }

    public Integer getResidentType() {
        return residentType;
    }

    public void setResidentType(Integer residentType) {
        this.residentType = residentType;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
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

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BuildingResident{" +
                "residentId='" + residentId + '\'' +
                ", residentName='" + residentName + '\'' +
                ", residentPhone='" + residentPhone + '\'' +
                ", residentIdentityCard='" + residentIdentityCard + '\'' +
                ", residentType=" + residentType +
                ", workPlace='" + workPlace + '\'' +
                ", floorId='" + floorId + '\'' +
                ", buildingUnitId='" + buildingUnitId + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyBy='" + modifyBy + '\'' +
                '}';
    }
}
