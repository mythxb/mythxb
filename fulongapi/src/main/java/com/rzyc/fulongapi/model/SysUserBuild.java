package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户楼栋、单元关系表
 * </p>
 *
 * @author 
 * @since 2022-03-16
 */
@ApiModel(value="SysUserBuild对象", description="用户楼栋、单元关系表")
public class SysUserBuild implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关系id")
    private String relationId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "楼栋、单元id")
    private String targetId;

    @ApiModelProperty(value = "类型 2、楼栋 3、单元")
    private Integer relationType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
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

    @Override
    public String toString() {
        return "SysUserBuild{" +
            "relationId=" + relationId +
            ", userId=" + userId +
            ", targetId=" + targetId +
            ", relationType=" + relationType +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
        "}";
    }
}
