package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="CheckDesc对象", description="")
public class CheckDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "检查项id")
    private String id;

    @ApiModelProperty(value = "检查id")
    private String checkId;

    @ApiModelProperty(value = "检查项id")
    private String checkItemId;

    @ApiModelProperty(value = "检查项")
    private String checkItemName;

    @ApiModelProperty(value = "检查结果 1.合格 2.不合格 3：不涉及 4：未选择")
    private Integer checkResult;

    @TableField(exist = false)
    @ApiModelProperty(value = "检查项分类")
    private String dangerTypeName;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    public String getDangerTypeName() {
        return dangerTypeName;
    }

    public void setDangerTypeName(String dangerTypeName) {
        this.dangerTypeName = dangerTypeName;
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
    public String getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }
    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }
    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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
        return "CheckDesc{" +
            "id=" + id +
            ", checkId=" + checkId +
            ", checkItemId=" + checkItemId +
            ", checkItemName=" + checkItemName +
            ", checkResult=" + checkResult +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
