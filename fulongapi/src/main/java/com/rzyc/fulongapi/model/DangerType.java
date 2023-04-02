package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="DangerType对象", description="")
public class DangerType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("隐患类型id")
    @TableId
    private String dangerTypeId;

    @ApiModelProperty("隐患类型")
    private String dangerTypeName;

    @ApiModelProperty("排序")
    private Integer sortId;

    private Date createTime;

    private String createBy;

    @TableField(exist = false)
    private Integer dangerCount;

    @TableField(exist = false)
    @ApiModelProperty("隐患总数")
    private Integer totalNum;

    @TableField(exist = false)
    @ApiModelProperty("已整改隐患")
    private Integer rectifyNum;

    @TableField(exist = false)
    @ApiModelProperty("整改中")
    private Integer rectifyingNum;

    @TableField(exist = false)
    @ApiModelProperty("未整改数")
    private Integer notRectifyNum;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getRectifyNum() {
        return rectifyNum;
    }

    public void setRectifyNum(Integer rectifyNum) {
        this.rectifyNum = rectifyNum;
    }

    public Integer getRectifyingNum() {
        return rectifyingNum;
    }

    public void setRectifyingNum(Integer rectifyingNum) {
        this.rectifyingNum = rectifyingNum;
    }

    public Integer getNotRectifyNum() {
        return notRectifyNum;
    }

    public void setNotRectifyNum(Integer notRectifyNum) {
        this.notRectifyNum = notRectifyNum;
    }

    public Integer getDangerCount() {
        return dangerCount;
    }

    public void setDangerCount(Integer dangerCount) {
        this.dangerCount = dangerCount;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getDangerTypeId() {
        return dangerTypeId;
    }

    public void setDangerTypeId(String dangerTypeId) {
        this.dangerTypeId = dangerTypeId;
    }
    public String getDangerTypeName() {
        return dangerTypeName;
    }

    public void setDangerTypeName(String dangerTypeName) {
        this.dangerTypeName = dangerTypeName;
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
        return "DangerType{" +
            "dangerTypeId=" + dangerTypeId +
            ", dangerTypeName=" + dangerTypeName +
            ", sortId=" + sortId +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
        "}";
    }
}
