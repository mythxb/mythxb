package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

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
 * @since 2022-02-24
 */
@ApiModel(value="FireEquipmentCategory对象", description="")
public class FireEquipmentCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String fecId;

    @ApiModelProperty(value = "消防类别")
    private String fecName;

    private String createBy;

    private Date createTime;

    @TableField(exist = false)
    private List<FireEquipment>fireEquipments;

    public List<FireEquipment> getFireEquipments() {
        return fireEquipments;
    }

    public void setFireEquipments(List<FireEquipment> fireEquipments) {
        this.fireEquipments = fireEquipments;
    }

    public String getFecId() {
        return fecId;
    }

    public void setFecId(String fecId) {
        this.fecId = fecId;
    }
    public String getFecName() {
        return fecName;
    }

    public void setFecName(String fecName) {
        this.fecName = fecName;
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
        return "FireEquipmentCategory{" +
            "fecId=" + fecId +
            ", fecName=" + fecName +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
        "}";
    }
}
