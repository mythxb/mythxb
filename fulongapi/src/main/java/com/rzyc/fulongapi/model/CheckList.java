package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
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
@ApiModel(value="CheckList对象", description="")
public class CheckList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "清单id")
    private String checkListId;

    @ApiModelProperty(value = "清单名称")
    private String listName;

    @ApiModelProperty(value = "清单类型 1：日常检查")
    private Integer listType;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public String getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(String checkListId) {
        this.checkListId = checkListId;
    }
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "CheckList{" +
            "checkListId=" + checkListId +
            ", listName=" + listName +
            ", listType=" + listType +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
