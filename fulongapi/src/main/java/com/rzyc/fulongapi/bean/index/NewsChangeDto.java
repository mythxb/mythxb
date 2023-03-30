package com.rzyc.fulongapi.bean.index;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("新闻修改或者新增")
public class NewsChangeDto {

    @ApiModelProperty(value = "新闻id 新增时前端创建uuid传",required = true)
    private String newsId;

    @ApiModelProperty(value = "新闻标题",required = true)
    private String title;

    @TableField(exist = false)
    @ApiModelProperty(value = "新闻内容",required = true)
    private String contentText;

    @ApiModelProperty(value = "封面图",required = true)
    private String cover;

    @ApiModelProperty(value = "作者",required = true)
    private String author;

    @ApiModelProperty(value = "是否置顶：1、是 2、否",required = true)
    private Integer topState;

    @ApiModelProperty(value = "是否banner：1、是 2、否",required = true)
    private Integer bannerState;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getTopState() {
        return topState;
    }

    public void setTopState(Integer topState) {
        this.topState = topState;
    }

    public Integer getBannerState() {
        return bannerState;
    }

    public void setBannerState(Integer bannerState) {
        this.bannerState = bannerState;
    }
}
