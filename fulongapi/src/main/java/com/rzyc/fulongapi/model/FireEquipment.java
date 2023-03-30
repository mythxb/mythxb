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
 * @since 2022-02-24
 */
@ApiModel(value="FireEquipment对象", description="")
public class FireEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String feId;

    @ApiModelProperty(value = "器材名称")
    private String feName;

    @ApiModelProperty(value = "器材单位")
    private String feUnit;

    @ApiModelProperty(value = "器材数量")
    private Integer feNumber;

    @ApiModelProperty(value = "分类ID")
    private String feCategoryId;

    @ApiModelProperty(value = "消防站ID")
    private String fsId;

    private Date createTime;

    private String createBy;

    public String getFeId() {
        return feId;
    }

    public void setFeId(String feId) {
        this.feId = feId;
    }
    public String getFeName() {
        return feName;
    }

    public void setFeName(String feName) {
        this.feName = feName;
    }
    public String getFeUnit() {
        return feUnit;
    }

    public void setFeUnit(String feUnit) {
        this.feUnit = feUnit;
    }
    public Integer getFeNumber() {
        return feNumber;
    }

    public void setFeNumber(Integer feNumber) {
        this.feNumber = feNumber;
    }
    public String getFeCategoryId() {
        return feCategoryId;
    }

    public void setFeCategoryId(String feCategoryId) {
        this.feCategoryId = feCategoryId;
    }
    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
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
        return "FireEquipment{" +
            "feId=" + feId +
            ", feName=" + feName +
            ", feUnit=" + feUnit +
            ", feNumber=" + feNumber +
            ", feCategoryId=" + feCategoryId +
            ", fsId=" + fsId +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
        "}";
    }
}
