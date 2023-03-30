package com.rzyc.fulongapi.bean.build;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("企业新增或者修改")
public class EntChangeDto {


    @TableId
    @ApiModelProperty(value = "企业id 传修改 不传新增")
    private String enterpriseId;

    @NotNull(message = "企业名不能为空")
    @ApiModelProperty(value = "企业名",required = true)
    private String entName;

    @NotNull(message = "楼栋不能为空")
    @ApiModelProperty(value = "楼栋id",required = true)
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String buildingUnitId;

    @ApiModelProperty(value = "楼层id")
    private String floorFkey;

    @NotNull(message = "行业不能为空")
    @ApiModelProperty(value = "行业id",required = true)
    private String industryId;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "负责人")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ApiModelProperty(value = "就业人数")
    private Integer workNum;

    @ApiModelProperty(value = "居住人数")
    private Integer personNum;

    @ApiModelProperty(value = "燃气灶具数")
    private Integer cookerNum;

    @ApiModelProperty(value = "燃气户表数")
    private Integer usertableNum;

    @ApiModelProperty(value = "热水器数")
    private Integer heaterNum;

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingUnitId() {
        return buildingUnitId;
    }

    public void setBuildingUnitId(String buildingUnitId) {
        this.buildingUnitId = buildingUnitId;
    }

    public String getFloorFkey() {
        return floorFkey;
    }

    public void setFloorFkey(String floorFkey) {
        this.floorFkey = floorFkey;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Integer getWorkNum() {
        return workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getCookerNum() {
        return cookerNum;
    }

    public void setCookerNum(Integer cookerNum) {
        this.cookerNum = cookerNum;
    }

    public Integer getUsertableNum() {
        return usertableNum;
    }

    public void setUsertableNum(Integer usertableNum) {
        this.usertableNum = usertableNum;
    }

    public Integer getHeaterNum() {
        return heaterNum;
    }

    public void setHeaterNum(Integer heaterNum) {
        this.heaterNum = heaterNum;
    }

    @Override
    public String toString() {
        return "EntChangeDto{" +
                "enterpriseId='" + enterpriseId + '\'' +
                ", entName='" + entName + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", buildingUnitId='" + buildingUnitId + '\'' +
                ", floorFkey='" + floorFkey + '\'' +
                ", industryId='" + industryId + '\'' +
                ", address='" + address + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", workNum=" + workNum +
                ", personNum=" + personNum +
                ", cookerNum=" + cookerNum +
                ", usertableNum=" + usertableNum +
                ", heaterNum=" + heaterNum +
                '}';
    }
}
