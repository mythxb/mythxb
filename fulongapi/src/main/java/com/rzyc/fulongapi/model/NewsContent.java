package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="NewsContent对象", description="")
public class NewsContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容id")
    private String contentId;

    @ApiModelProperty(value = "新闻id")
    private String newsId;

    @ApiModelProperty(value = "新闻文本")
    private String contentText;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;



    @ApiModelProperty(value = "修改人")
    private String modifyBy;





    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
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

    @Override
    public String toString() {
        return "NewsContent{" +
            "contentId=" + contentId +
            ", newsId=" + newsId +
            ", contentText=" + contentText +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
