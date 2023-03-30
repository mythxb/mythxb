package com.rzyc.fulongapi.bean.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rzyc.fulongapi.model.CheckDesc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("检查列表")
public class ChecksList {


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
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    @ApiModelProperty(value = "清单id")
    private String listId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检查类型：1、入户检查 2、公共区域检查 3、企业检查")
    private Integer checkType;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "楼层")
    private Integer floorNumber;

    @ApiModelProperty(value = "单元名")
    private String unitName;

    @ApiModelProperty(value = "单元负责人")
    private String unitManager;

    @ApiModelProperty(value = "楼栋名")
    private String buildName;

    @ApiModelProperty(value = "检查人")
    private String userName;

    @ApiModelProperty("检查项")
    private List<CheckDesc> checkDescs;

    @ApiModelProperty("企业名")
    private String entName;

    private String sbName;

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public List<CheckDesc> getCheckDescs() {
        return checkDescs;
    }

    public void setCheckDescs(List<CheckDesc> checkDescs) {
        this.checkDescs = checkDescs;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(String unitManager) {
        this.unitManager = unitManager;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
