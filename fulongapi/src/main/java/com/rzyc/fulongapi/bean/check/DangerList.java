package com.rzyc.fulongapi.bean.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("隐患列表")
public class DangerList {

    @ApiModelProperty(value = "隐患id")
    private String id;

    @ApiModelProperty(value = "检查id")
    private String checkId;

    @ApiModelProperty(value = "检查项id")
    private String checkDescId;

    @ApiModelProperty(value = "整改结果")
    private String rectifyResult;

    @ApiModelProperty(value = "整改建议")
    private String checkProposal;

    @ApiModelProperty(value = "隐患问题")
    private String checkItem;

    @ApiModelProperty(value = "整改信息")
    private String rectifyInfo;

    @ApiModelProperty(value = "整改期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date rectifyTerm;

    @ApiModelProperty(value = "1.一般隐患 2.重大隐患 3.需要专家确定的重大隐患")
    private Integer normalorimportant;

    @ApiModelProperty(value = "1.未整改 2.整改中 3.已整改 4.无法整改")
    private Integer rectificationState;

    @ApiModelProperty(value = "建筑体id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String buildingUnitId;

    @ApiModelProperty(value = "楼层外键")
    private String floorFkey;

    @ApiModelProperty(value = "检查类型：1、入户检查 2、公共区域检查 3、企业检查 4.特殊建筑体")
    private Integer checkType;

    @ApiModelProperty("隐患类型id")
    private String dangerTypeId;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty("修改人")
    private String modifyBy;

    @ApiModelProperty("楼栋名")
    private String buildName;

    @ApiModelProperty("单元名")
    private String unitName;

    @ApiModelProperty("单元号")
    private String unitNumber;

    @ApiModelProperty("单元管理人员")
    private String unitManager;

    @ApiModelProperty("单元管理人员联系方式")
    private String unitManagerContact;

    @ApiModelProperty("层数")
    private String floorNumber;

    @ApiModelProperty("企业名")
    private String entName;

    @ApiModelProperty("检查人")
    private String userName;

    @ApiModelProperty("隐患类型")
    private String dangerTypeName;

    @ApiModelProperty("特殊建筑名字")
    private String sbName;

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getDangerTypeName() {
        return dangerTypeName;
    }

    public void setDangerTypeName(String dangerTypeName) {
        this.dangerTypeName = dangerTypeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getRectifyTerm() {
        return rectifyTerm;
    }

    public void setRectifyTerm(Date rectifyTerm) {
        this.rectifyTerm = rectifyTerm;
    }

    public String getRectifyInfo() {
        return rectifyInfo;
    }

    public void setRectifyInfo(String rectifyInfo) {
        this.rectifyInfo = rectifyInfo;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
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

    public String getUnitManagerContact() {
        return unitManagerContact;
    }

    public void setUnitManagerContact(String unitManagerContact) {
        this.unitManagerContact = unitManagerContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckDescId() {
        return checkDescId;
    }

    public void setCheckDescId(String checkDescId) {
        this.checkDescId = checkDescId;
    }

    public String getRectifyResult() {
        return rectifyResult;
    }

    public void setRectifyResult(String rectifyResult) {
        this.rectifyResult = rectifyResult;
    }

    public String getCheckProposal() {
        return checkProposal;
    }

    public void setCheckProposal(String checkProposal) {
        this.checkProposal = checkProposal;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public Integer getNormalorimportant() {
        return normalorimportant;
    }

    public void setNormalorimportant(Integer normalorimportant) {
        this.normalorimportant = normalorimportant;
    }

    public Integer getRectificationState() {
        return rectificationState;
    }

    public void setRectificationState(Integer rectificationState) {
        this.rectificationState = rectificationState;
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

    public String getDangerTypeId() {
        return dangerTypeId;
    }

    public void setDangerTypeId(String dangerTypeId) {
        this.dangerTypeId = dangerTypeId;
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

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }
}
