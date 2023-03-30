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
 *
 * </p>
 *
 * @author
 * @since 2022-06-12
 */
@TableName("build_unit_level")
@ApiModel(value="BuildUnitLevel对象", description="")
public class BuildUnitLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房屋等级id")
    @TableId("level_id")
    private String levelId;

    @ApiModelProperty(value = "建筑id")
    @TableField("building_id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    @TableField("unit_id")
    private String unitId;

    @ApiModelProperty(value = "类型（统规自建、购地自建、单位自建或其它）")
    @TableField("house_type")
    private String houseType;

    @ApiModelProperty(value = "有无报规报建手续")
    @TableField("plan_info")
    private String planInfo;

    @ApiModelProperty(value = "建筑面积（㎡）")
    @TableField("area_num")
    private String areaNum;

    @ApiModelProperty(value = "结构形式")
    @TableField("structure_type")
    private String structureType;

    @ApiModelProperty(value = "层数")
    @TableField("layer_num")
    private String layerNum;

    @ApiModelProperty(value = "是否加建")
    @TableField("additional")
    private String additional;

    @ApiModelProperty(value = "加建层数")
    @TableField("additional_num")
    private String additionalNum;

    @ApiModelProperty(value = "加建时间")
    @TableField("additional_time")
    private String additionalTime;

    @ApiModelProperty(value = "是否改变房屋使用功能")
    @TableField("change_function")
    private String changeFunction;

    @ApiModelProperty(value = "房屋使用情况")
    @TableField("use_info")
    private String useInfo;

    @ApiModelProperty(value = "是否改造（结构）")
    @TableField("reform")
    private String reform;

    @ApiModelProperty(value = "改造情况")
    private String reformInfo;

    @ApiModelProperty(value = "改造时间")
    private String reformTime;

    @ApiModelProperty(value = "地基基础")
    @TableField("foundation")
    private String foundation;

    @ApiModelProperty(value = "上部结构")
    @TableField("superstructure")
    private String superstructure;

    @ApiModelProperty(value = "上部结构体系及整体性")
    @TableField("entirety")
    private String entirety;

    @ApiModelProperty(value = "问题部位照片")
    @TableField("problem_img")
    private String problemImg;

    @ApiModelProperty(value = "是否需要进行安全鉴定")
    @TableField("safety_appraisal")
    private String safetyAppraisal;

    @ApiModelProperty(value = "问题处理建议")
    @TableField("proposal_desc")
    private String proposalDesc;

    @ApiModelProperty(value = "正立面照片")
    @TableField("facade_img")
    private String facadeImg;

    @ApiModelProperty(value = "基础照片")
    @TableField("base_img")
    private String baseImg;

    @ApiModelProperty(value = "有无隔断")
    @TableField("partition_desc")
    private String partitionDesc;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_by")
    private String modifyBy;

    @ApiModelProperty(value = "修改人")
    @TableField("modify_time")
    private Date modifyTime;

    public String getReformInfo() {
        return reformInfo;
    }

    public void setReformInfo(String reformInfo) {
        this.reformInfo = reformInfo;
    }

    public String getReformTime() {
        return reformTime;
    }

    public void setReformTime(String reformTime) {
        this.reformTime = reformTime;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
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
    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
    }
    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }
    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }
    public String getLayerNum() {
        return layerNum;
    }

    public void setLayerNum(String layerNum) {
        this.layerNum = layerNum;
    }
    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
    public String getAdditionalNum() {
        return additionalNum;
    }

    public void setAdditionalNum(String additionalNum) {
        this.additionalNum = additionalNum;
    }
    public String getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(String additionalTime) {
        this.additionalTime = additionalTime;
    }
    public String getChangeFunction() {
        return changeFunction;
    }

    public void setChangeFunction(String changeFunction) {
        this.changeFunction = changeFunction;
    }
    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }
    public String getReform() {
        return reform;
    }

    public void setReform(String reform) {
        this.reform = reform;
    }
    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }
    public String getSuperstructure() {
        return superstructure;
    }

    public void setSuperstructure(String superstructure) {
        this.superstructure = superstructure;
    }
    public String getEntirety() {
        return entirety;
    }

    public void setEntirety(String entirety) {
        this.entirety = entirety;
    }
    public String getProblemImg() {
        return problemImg;
    }

    public void setProblemImg(String problemImg) {
        this.problemImg = problemImg;
    }
    public String getSafetyAppraisal() {
        return safetyAppraisal;
    }

    public void setSafetyAppraisal(String safetyAppraisal) {
        this.safetyAppraisal = safetyAppraisal;
    }

    public String getProposalDesc() {
        return proposalDesc;
    }

    public void setProposalDesc(String proposalDesc) {
        this.proposalDesc = proposalDesc;
    }

    public String getFacadeImg() {
        return facadeImg;
    }

    public void setFacadeImg(String facadeImg) {
        this.facadeImg = facadeImg;
    }
    public String getBaseImg() {
        return baseImg;
    }

    public void setBaseImg(String baseImg) {
        this.baseImg = baseImg;
    }

    public String getPartitionDesc() {
        return partitionDesc;
    }

    public void setPartitionDesc(String partitionDesc) {
        this.partitionDesc = partitionDesc;
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
        return "BuildUnitLevel{" +
            "levelId=" + levelId +
            ", buildingId=" + buildingId +
            ", unitId=" + unitId +
            ", houseType=" + houseType +
            ", planInfo=" + planInfo +
            ", areaNum=" + areaNum +
            ", structureType=" + structureType +
            ", layerNum=" + layerNum +
            ", additional=" + additional +
            ", additionalNum=" + additionalNum +
            ", additionalTime=" + additionalTime +
            ", changeFunction=" + changeFunction +
            ", useInfo=" + useInfo +
            ", reform=" + reform +
            ", foundation=" + foundation +
            ", superstructure=" + superstructure +
            ", entirety=" + entirety +
            ", problemImg=" + problemImg +
            ", safetyAppraisal=" + safetyAppraisal +
            ", proposalDesc=" + proposalDesc +
            ", facadeImg=" + facadeImg +
            ", baseImg=" + baseImg +
            ", partition=" + partitionDesc +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
