package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.common.utils.excel.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-23
 */
@ApiModel(value="Enterprise对象", description="")
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "企业id")
    private String enterpriseId;

    @ExcelColumn(value = "企业名",col = 1)
    @ApiModelProperty(value = "企业名")
    private String entName;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @ApiModelProperty(value = "单元id")
    private String buildingUnitId;

    @ApiModelProperty(value = "楼层id")
    private String floorFkey;

    @ApiModelProperty(value = "行业")
    private String industryId;

    @ExcelColumn(value = "行业名",col = 2)
    @TableField(exist = false)
    @ApiModelProperty(value = "行业名")
    private String industryName;

    @ExcelColumn(value = "地址",col = 3)
    @ApiModelProperty(value = "地址")
    private String address;

    @ExcelColumn(value = "负责人",col = 4)
    @ApiModelProperty(value = "负责人")
    private String contacts;

    @ExcelColumn(value = "联系电话",col = 5)
    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ExcelColumn(value = "就业人数",col = 6)
    @ApiModelProperty(value = "就业人数")
    private Integer workNum;

    @ExcelColumn(value = "居住人数",col = 7)
    @ApiModelProperty(value = "居住人数")
    private Integer personNum;

    @ExcelColumn(value = "燃气灶具数",col = 8)
    @ApiModelProperty(value = "燃气灶具数")
    private Integer cookerNum;

    @ExcelColumn(value = "燃气户表数",col = 9)
    @ApiModelProperty(value = "燃气户表数")
    private Integer usertableNum;

    @ExcelColumn(value = "热水器数",col = 10)
    @ApiModelProperty(value = "热水器数")
    private Integer heaterNum;

    @ExcelColumn(value = "风险等级 红：4 橙：3 黄：2 蓝：1",col = 11)
    @ApiModelProperty(value = "风险等级 红：4 橙：3 黄：2 蓝：1")
    private Integer riskLevel;

    @ApiModelProperty("二维码")
    private String qrCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

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
    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
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
        return "Enterprise{" +
            "enterpriseId=" + enterpriseId +
            ", entName=" + entName +
            ", buildingId=" + buildingId +
            ", buildingUnitId=" + buildingUnitId +
            ", floorFkey=" + floorFkey +
            ", industryId=" + industryId +
            ", address=" + address +
            ", contacts=" + contacts +
            ", contactMobile=" + contactMobile +
            ", workNum=" + workNum +
            ", personNum=" + personNum +
            ", cookerNum=" + cookerNum +
            ", usertableNum=" + usertableNum +
            ", heaterNum=" + heaterNum +
            ", riskLevel=" + riskLevel +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
