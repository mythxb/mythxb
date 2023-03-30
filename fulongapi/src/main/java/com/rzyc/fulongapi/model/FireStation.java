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
@ApiModel(value="FireStation对象", description="")
public class FireStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String fsId;

    @ApiModelProperty(value = "消防站名称")
    private String fsName;

    @ApiModelProperty(value = "消防站地址")
    private String fsAddress;

    @ApiModelProperty(value = "消防站电话")
    private String fsContact;

    @ApiModelProperty(value = "消防站负责人")
    private String fsManager;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    public String getFsManager() {
        return fsManager;
    }

    public void setFsManager(String fsManager) {
        this.fsManager = fsManager;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }
    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }
    public String getFsAddress() {
        return fsAddress;
    }

    public void setFsAddress(String fsAddress) {
        this.fsAddress = fsAddress;
    }
    public String getFsContact() {
        return fsContact;
    }

    public void setFsContact(String fsContact) {
        this.fsContact = fsContact;
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
        return "FireStation{" +
            "fsId=" + fsId +
            ", fsName=" + fsName +
            ", fsAddress=" + fsAddress +
            ", fsContact=" + fsContact +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
