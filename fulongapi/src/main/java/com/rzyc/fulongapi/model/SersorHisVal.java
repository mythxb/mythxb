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
 * 传感器历史数据
 * </p>
 *
 * @author 
 * @since 2023-05-27
 */
@TableName("sersor_his_val")
@ApiModel(value="SersorHisVal对象", description="传感器历史数据")
public class SersorHisVal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "传感器历史数据")
    @TableId("his_id")
    private String hisId;

    @ApiModelProperty(value = "传感器id")
    @TableField("sersor_id")
    private String sersorId;

    @ApiModelProperty(value = "传感器值")
    @TableField("current_val")
    private String currentVal;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }
    public String getSersorId() {
        return sersorId;
    }

    public void setSersorId(String sersorId) {
        this.sersorId = sersorId;
    }
    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        return "SersorHisVal{" +
            "hisId=" + hisId +
            ", sersorId=" + sersorId +
            ", currentVal=" + currentVal +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
        "}";
    }
}
