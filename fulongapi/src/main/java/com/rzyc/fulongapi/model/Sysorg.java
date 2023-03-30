package com.rzyc.fulongapi.model;

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
 * @since 2022-02-17
 */
@ApiModel(value="Sysorg对象", description="")
public class Sysorg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("OrgCode")
    private String OrgCode;

    @TableField("OrgName")
    private String OrgName;

    @TableField("OrgLevel")
    private Integer OrgLevel;

    @TableField("SuperiorOrgCode")
    private String SuperiorOrgCode;

    @TableField("OrgLongitude")
    private String OrgLongitude;

    @TableField("OrgLatitude")
    private String OrgLatitude;

    @ApiModelProperty(value = "负责人")
    @TableField("Principal")
    private String Principal;

    @ApiModelProperty(value = "联系方式")
    @TableField("MobileTel")
    private String MobileTel;

    @TableField("CreatedBy")
    private String CreatedBy;

    @TableField("CreatedOn")
    private Date CreatedOn;

    @TableField("ModifiedBy")
    private String ModifiedBy;

    @TableField("ModifiedOn")
    private Date ModifiedOn;

    public String getOrgCode() {
        return OrgCode;
    }

    public void setOrgCode(String OrgCode) {
        this.OrgCode = OrgCode;
    }
    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }
    public Integer getOrgLevel() {
        return OrgLevel;
    }

    public void setOrgLevel(Integer OrgLevel) {
        this.OrgLevel = OrgLevel;
    }
    public String getSuperiorOrgCode() {
        return SuperiorOrgCode;
    }

    public void setSuperiorOrgCode(String SuperiorOrgCode) {
        this.SuperiorOrgCode = SuperiorOrgCode;
    }
    public String getOrgLongitude() {
        return OrgLongitude;
    }

    public void setOrgLongitude(String OrgLongitude) {
        this.OrgLongitude = OrgLongitude;
    }
    public String getOrgLatitude() {
        return OrgLatitude;
    }

    public void setOrgLatitude(String OrgLatitude) {
        this.OrgLatitude = OrgLatitude;
    }
    public String getPrincipal() {
        return Principal;
    }

    public void setPrincipal(String Principal) {
        this.Principal = Principal;
    }
    public String getMobileTel() {
        return MobileTel;
    }

    public void setMobileTel(String MobileTel) {
        this.MobileTel = MobileTel;
    }
    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }
    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date CreatedOn) {
        this.CreatedOn = CreatedOn;
    }
    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }
    public Date getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(Date ModifiedOn) {
        this.ModifiedOn = ModifiedOn;
    }

    @Override
    public String toString() {
        return "Sysorg{" +
            "OrgCode=" + OrgCode +
            ", OrgName=" + OrgName +
            ", OrgLevel=" + OrgLevel +
            ", SuperiorOrgCode=" + SuperiorOrgCode +
            ", OrgLongitude=" + OrgLongitude +
            ", OrgLatitude=" + OrgLatitude +
            ", Principal=" + Principal +
            ", MobileTel=" + MobileTel +
            ", CreatedBy=" + CreatedBy +
            ", CreatedOn=" + CreatedOn +
            ", ModifiedBy=" + ModifiedBy +
            ", ModifiedOn=" + ModifiedOn +
        "}";
    }
}
