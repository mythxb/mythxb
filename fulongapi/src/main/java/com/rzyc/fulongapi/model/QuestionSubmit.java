package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-03-16
 */
@ApiModel(value="QuestionSubmit对象", description="")
public class QuestionSubmit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String qsSubId;

    @ApiModelProperty(value = "隐患类型")
    private String dangerType;

    @ApiModelProperty(value = "问题详细")
    private String qsSubDesc;

    @ApiModelProperty(value = "名称")
    private String subUserName;

    @ApiModelProperty(value = "电话")
    private String subUserPhone;

    @ApiModelProperty(value = "1.未处理 2.已处理")
    private Integer qsState;

    @ApiModelProperty(value = "处理结果")
    private String resolvedResult;

    @ApiModelProperty(value = "楼栋ID")
    private String buildingId;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getQsSubId() {
        return qsSubId;
    }

    public void setQsSubId(String qsSubId) {
        this.qsSubId = qsSubId;
    }
    public String getDangerType() {
        return dangerType;
    }

    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }
    public String getQsSubDesc() {
        return qsSubDesc;
    }

    public void setQsSubDesc(String qsSubDesc) {
        this.qsSubDesc = qsSubDesc;
    }
    public String getSubUserName() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName = subUserName;
    }
    public String getSubUserPhone() {
        return subUserPhone;
    }

    public void setSubUserPhone(String subUserPhone) {
        this.subUserPhone = subUserPhone;
    }
    public Integer getQsState() {
        return qsState;
    }

    public void setQsState(Integer qsState) {
        this.qsState = qsState;
    }
    public String getResolvedResult() {
        return resolvedResult;
    }

    public void setResolvedResult(String resolvedResult) {
        this.resolvedResult = resolvedResult;
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
        return "QuestionSubmit{" +
            "qsSubId=" + qsSubId +
            ", dangerType=" + dangerType +
            ", qsSubDesc=" + qsSubDesc +
            ", subUserName=" + subUserName +
            ", subUserPhone=" + subUserPhone +
            ", qsState=" + qsState +
            ", resolvedResult=" + resolvedResult +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
