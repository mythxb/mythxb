package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-22
 */
@ApiModel(value="Checks对象", description="")
public class Checks implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "检查id")
    private String checkId;

    @ApiModelProperty(value = "建筑体id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String buildingUnitId;

    @ApiModelProperty(value = "楼层外键")
    private String floorFkey;

    @ApiModelProperty(value = "在多少楼层")
    private Integer atFloor;

    @ApiModelProperty(value = "检查人员")
    private String checkUserId;

    @ApiModelProperty(value = "检查时间")
    private Date checkTime;

    @ApiModelProperty(value = "清单id")
    private String listId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检查类型：1、入户检查 2、公共区域检查 3、企业检查")
    private Integer checkType;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
    public String getBuildingUnitId() {
        return buildingUnitId;
    }

    public void setBuildingUnitId(String buildingUnitId) {
        this.buildingUnitId = buildingUnitId;
    }
    public String getFloorFkey() {
        return floorFkey;
    }

    public void setFloorFkey(String floorFkey) {
        this.floorFkey = floorFkey;
    }
    public Integer getAtFloor() {
        return atFloor;
    }

    public void setAtFloor(Integer atFloor) {
        this.atFloor = atFloor;
    }
    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    @Override
    public String toString() {
        return "Checks{" +
            "checkId=" + checkId +
            ", buildingId=" + buildingId +
            ", buildingUnitId=" + buildingUnitId +
            ", floorFkey=" + floorFkey +
            ", atFloor=" + atFloor +
            ", checkUserId=" + checkUserId +
            ", checkTime=" + checkTime +
            ", listId=" + listId +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
