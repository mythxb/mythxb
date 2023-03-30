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
 * @since 2022-02-21
 */
@ApiModel(value = "CheckItem对象", description = "")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "检查项id")
    private String itemId;

    @ApiModelProperty(value = "检查项名称")
    private String checkItemName;

    @ApiModelProperty(value = "父分类id")
    private String classId;

    @ApiModelProperty(value = "行业id")
    private String industryId;

    @ApiModelProperty(value = "检查项内容")
    private String itemContent;

    @TableField(exist = false)
    @ApiModelProperty("隐患分类")
    private String dangerTypeName;

    @ApiModelProperty(value = "法律依据")
    private String itemLegalBasis;

    //@TableField( exist = false)
    @ApiModelProperty(value = "检查帮助")
    private String checkHelp;

    private Date createTime;

    private String createBy;


    @TableField( exist = false)
    @ApiModelProperty(value = "行业名")
    private String industryName;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getCheckHelp() {
        return checkHelp;
    }

    public void setCheckHelp(String checkHelp) {
        this.checkHelp = checkHelp;
    }

    public String getDangerTypeName() {
        return dangerTypeName;
    }

    public void setDangerTypeName(String dangerTypeName) {
        this.dangerTypeName = dangerTypeName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemLegalBasis() {
        return itemLegalBasis;
    }

    public void setItemLegalBasis(String itemLegalBasis) {
        this.itemLegalBasis = itemLegalBasis;
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
        return "CheckItem{" +
                "itemId=" + itemId +
                ", checkItemName=" + checkItemName +
                ", classId=" + classId +
                ", itemContent=" + itemContent +
                ", itemLegalBasis=" + itemLegalBasis +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                "}";
    }
}
