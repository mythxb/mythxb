package com.rzyc.fulongapi.model;

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
 * @since 2022-02-22
 */
@ApiModel(value="单元管理员", description="")
public class BuildUnitHouseholder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String householderId;

    @ApiModelProperty(value = "单元外键")
    private String buildingUnitId;

    @ApiModelProperty(value = "管理员")
    private String householderName;

    @ApiModelProperty(value = "管理员电话")
    private String householderPhone;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;

    public String getHouseholderId() {
        return householderId;
    }

    public void setHouseholderId(String householderId) {
        this.householderId = householderId;
    }
    public String getBuildingUnitId() {
        return buildingUnitId;
    }

    public void setBuildingUnitId(String buildingUnitId) {
        this.buildingUnitId = buildingUnitId;
    }
    public String getHouseholderName() {
        return householderName;
    }

    public void setHouseholderName(String householderName) {
        this.householderName = householderName;
    }
    public String getHouseholderPhone() {
        return householderPhone;
    }

    public void setHouseholderPhone(String householderPhone) {
        this.householderPhone = householderPhone;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BuildUnitHouseholder{" +
            "householderId=" + householderId +
            ", buildingUnitId=" + buildingUnitId +
            ", householderName=" + householderName +
            ", householderPhone=" + householderPhone +
            ", createTime=" + createTime +
        "}";
    }
}
