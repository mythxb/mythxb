package com.rzyc.fulongapi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-03-03
 */
@ApiModel(value="SpecialBuilding对象", description="")
public class SpecialBuilding implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String sbId;

    @ApiModelProperty(value = "建筑名称")
    private String sbName;

    @ApiModelProperty(value = "建成时间")
    private Date buildTime;

    @ApiModelProperty(value = "富文本")
    private String richText;

    private String createBy;

    private String qrCode;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSbId() {
        return sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }
    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }
    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }
    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SpecialBuilding{" +
            "sbId=" + sbId +
            ", sbName=" + sbName +
            ", buildTime=" + buildTime +
            ", richText=" + richText +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
        "}";
    }
}
