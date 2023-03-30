package com.rzyc.fulongapi.bean.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("组织架构修改或新增")
public class OrgChangeDto {

    @ApiModelProperty(value = "单位id 传新增 不传修改")
    private String organizationId;

    @ApiModelProperty(value = "单位名称")
    private String organizationName;

    @ApiModelProperty(value = "父级单位id")
    private String parentId;

    @ApiModelProperty(value = "单位类型 1：部门 2：岗位")
    private Integer organizationType;

    @ApiModelProperty(value = "负责人")
    private String personName;

    @ApiModelProperty(value = "工作内容")
    private String jobContent;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Integer organizationType) {
        this.organizationType = organizationType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }
}


