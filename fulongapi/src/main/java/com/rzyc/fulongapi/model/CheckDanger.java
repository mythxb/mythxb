package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-21
 */
@ApiModel(value="CheckDanger对象", description="")
public class CheckDanger implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;


    @ApiModelProperty(value = "检查id")
    private String checkId;

    @ApiModelProperty(value = "检查项id")
    private String checkDescId;

    @ApiModelProperty(value = "整改建议")
    private String checkProposal;

    @ApiModelProperty(value = "隐患问题")
    private String checkItem;

    @ApiModelProperty(value = "整改信息")
    private String rectifyInfo;

    @ApiModelProperty(value = "1.一般隐患 2.重大隐患 3.需要专家确定的重大隐患")
    private Integer normalorimportant;

    @ApiModelProperty(value = "1.未整改 2.整改中 3.已整改 4.预期未整改 5.无法整改")
    private Integer rectificationState;

    @ApiModelProperty(value = "建筑体id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String buildingUnitId;

    @ApiModelProperty(value = "楼层外键")
    private String floorFkey;

    private String dangerTypeId;

    @ApiModelProperty(value = "整改期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date rectifyTerm;

    @ApiModelProperty(value = "检查类型：1、入户检查 2、公共区域检查 3、企业检查")
    private Integer checkType;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;


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

    public String getDangerTypeId() {
        return dangerTypeId;
    }

    public void setDangerTypeId(String dangerTypeId) {
        this.dangerTypeId = dangerTypeId;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getCheckId() {
        return checkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCheckProposal() {
        return checkProposal;
    }

    public void setCheckProposal(String checkProposal) {
        this.checkProposal = checkProposal;
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
        return "CheckDanger{" +
            "id=" + id +
            ", checkId=" + checkId +
            ", checkDescId=" + checkDescId +
            ", checkProposal=" + checkProposal +
            ", normalorimportant=" + normalorimportant +
            ", rectificationState=" + rectificationState +
            ", buildingId=" + buildingId +
            ", buildingUnitId=" + buildingUnitId +
            ", floorFkey=" + floorFkey +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
