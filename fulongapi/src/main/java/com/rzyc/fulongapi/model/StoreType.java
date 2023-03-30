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
@ApiModel(value="StoreType对象", description="")
public class StoreType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private Integer sortId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    private Date createTime;

    private String createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        return "StoreType{" +
            "id=" + id +
            ", typeName=" + typeName +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
        "}";
    }
}
