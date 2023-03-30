package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-03-01
 */
@ApiModel(value="SysResource对象", description="")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "资源名")
    private String name;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "component")
    private String component;

    @ApiModelProperty(value = "meta")
    private String metas;

    @TableField(exist = false)
    @ApiModelProperty("metas")
    private Map<String,String> meta;

    @ApiModelProperty(value = "父级菜单")
    private String parentId;

    @ApiModelProperty(value = "redirect")
    private String redirect;

    @ApiModelProperty(value = "排序")
    private Integer sortId;

    @ApiModelProperty(value = "是否隐藏")
    private String hiddens;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "1.首页 2.检测业务协同管理 3.能力验证管理 4.技术指导管理 5.培训/会议管理 6.问卷调查管理 7.检测资源管理 8.系统设置")
    private Integer resourceType;

    @ApiModelProperty(value = "是否显示")
    private String alwaysShow;

    @TableField(exist = false)
    @ApiModelProperty(value = "子节点")
    private List<SysResource> children;


    public String getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(String alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public List<SysResource> getChildren() {
        return children;
    }

    public void setChildren(List<SysResource> children) {
        this.children = children;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
    public String getHiddens() {
        return hiddens;
    }

    public void setHiddens(String hiddens) {
        this.hiddens = hiddens;
    }
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return "SysResource{" +
            "resourceId=" + resourceId +
            ", name=" + name +
            ", path=" + path +
            ", component=" + component +
            ", metas=" + metas +
            ", parentId=" + parentId +
            ", redirect=" + redirect +
            ", sortId=" + sortId +
            ", hiddens=" + hiddens +
            ", modifyBy=" + modifyBy +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", createTime=" + createTime +
            ", resourceType=" + resourceType +
        "}";
    }
}
