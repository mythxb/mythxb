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
 * @since 2022-05-12
 */
@ApiModel(value="EpidemicHistory对象", description="")
public class EpidemicHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "疫情记录id")
    private String epidemicId;

    @ApiModelProperty(value = "街道名")
    private String streetName;

    @ApiModelProperty(value = "社区名")
    private String communityName;

    @ApiModelProperty(value = "姓名")
    private String chinaName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "身份证号")
    private String cardId;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "境外/经由中高风险地区/重点地区")
    private String keyArea;

    @ApiModelProperty(value = "14日内旅居史")
    private String sojournHistory;

    @ApiModelProperty(value = "旅居史省")
    private String sojournProvince;

    @ApiModelProperty(value = "旅居史市")
    private String sojournCity;

    @ApiModelProperty(value = "旅居史区")
    private String sojournDistrict;

    @ApiModelProperty(value = "详细地区")
    private String sojournAddress;

    @ApiModelProperty(value = "风险等级（低、中、高）")
    private String sojournLevel;

    @ApiModelProperty(value = "来蓉交通方式")
    private String traffic;

    @ApiModelProperty(value = "出发时间")
    private String setoutTime;

    @ApiModelProperty(value = "抵蓉时间")
    private String arriveTime;

    @ApiModelProperty(value = "纳入社区管理时间")
    private String managerTime;

    @ApiModelProperty(value = "户籍地址")
    private String censusRegister;

    @ApiModelProperty(value = "在蓉居住地址")
    private String arriveAddress;

    @ApiModelProperty(value = "最近一次核酸检测情况")
    private String testingTime;

    @ApiModelProperty(value = "完成或未完成")
    private String testingState;

    @ApiModelProperty(value = "隔离状态")
    private String isolateState;

    @ApiModelProperty(value = "居观开始时间")
    private String startTime;

    @ApiModelProperty(value = "社区服务姓名")
    private String communityPerson;

    @ApiModelProperty(value = "社区服务联系电话")
    private String communityMobile;

    @ApiModelProperty(value = "社区工作单位及职务")
    private String communityWork;

    @ApiModelProperty(value = "医疗卫生机构服务姓名")
    private String medicalPerson;

    @ApiModelProperty(value = "医疗卫生机构服务联系电话")
    private String medicalMobile;

    @ApiModelProperty(value = "医疗卫生机构工作单位及职务")
    private String medicalWork;

    @ApiModelProperty(value = "物业联系人")
    private String propertyer;

    @ApiModelProperty(value = "物业联系电话")
    private String propertyPhone;

    @ApiModelProperty(value = "街道联系人")
    private String streeter;

    @ApiModelProperty(value = "街道联系电话")
    private String streetPhone;

    @ApiModelProperty(value = "社区民警")
    private String communityer;

    @ApiModelProperty(value = "社区民警联系电话")
    private String communityPhone;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    public String getEpidemicId() {
        return epidemicId;
    }

    public void setEpidemicId(String epidemicId) {
        this.epidemicId = epidemicId;
    }
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getKeyArea() {
        return keyArea;
    }

    public void setKeyArea(String keyArea) {
        this.keyArea = keyArea;
    }
    public String getSojournHistory() {
        return sojournHistory;
    }

    public void setSojournHistory(String sojournHistory) {
        this.sojournHistory = sojournHistory;
    }
    public String getSojournProvince() {
        return sojournProvince;
    }

    public void setSojournProvince(String sojournProvince) {
        this.sojournProvince = sojournProvince;
    }
    public String getSojournCity() {
        return sojournCity;
    }

    public void setSojournCity(String sojournCity) {
        this.sojournCity = sojournCity;
    }
    public String getSojournDistrict() {
        return sojournDistrict;
    }

    public void setSojournDistrict(String sojournDistrict) {
        this.sojournDistrict = sojournDistrict;
    }
    public String getSojournAddress() {
        return sojournAddress;
    }

    public void setSojournAddress(String sojournAddress) {
        this.sojournAddress = sojournAddress;
    }
    public String getSojournLevel() {
        return sojournLevel;
    }

    public void setSojournLevel(String sojournLevel) {
        this.sojournLevel = sojournLevel;
    }
    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }
    public String getSetoutTime() {
        return setoutTime;
    }

    public void setSetoutTime(String setoutTime) {
        this.setoutTime = setoutTime;
    }
    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
    public String getManagerTime() {
        return managerTime;
    }

    public void setManagerTime(String managerTime) {
        this.managerTime = managerTime;
    }
    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }
    public String getArriveAddress() {
        return arriveAddress;
    }

    public void setArriveAddress(String arriveAddress) {
        this.arriveAddress = arriveAddress;
    }
    public String getTestingTime() {
        return testingTime;
    }

    public void setTestingTime(String testingTime) {
        this.testingTime = testingTime;
    }
    public String getTestingState() {
        return testingState;
    }

    public void setTestingState(String testingState) {
        this.testingState = testingState;
    }
    public String getIsolateState() {
        return isolateState;
    }

    public void setIsolateState(String isolateState) {
        this.isolateState = isolateState;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getCommunityPerson() {
        return communityPerson;
    }

    public void setCommunityPerson(String communityPerson) {
        this.communityPerson = communityPerson;
    }
    public String getCommunityMobile() {
        return communityMobile;
    }

    public void setCommunityMobile(String communityMobile) {
        this.communityMobile = communityMobile;
    }
    public String getCommunityWork() {
        return communityWork;
    }

    public void setCommunityWork(String communityWork) {
        this.communityWork = communityWork;
    }
    public String getMedicalPerson() {
        return medicalPerson;
    }

    public void setMedicalPerson(String medicalPerson) {
        this.medicalPerson = medicalPerson;
    }
    public String getMedicalMobile() {
        return medicalMobile;
    }

    public void setMedicalMobile(String medicalMobile) {
        this.medicalMobile = medicalMobile;
    }
    public String getMedicalWork() {
        return medicalWork;
    }

    public void setMedicalWork(String medicalWork) {
        this.medicalWork = medicalWork;
    }
    public String getPropertyer() {
        return propertyer;
    }

    public void setPropertyer(String propertyer) {
        this.propertyer = propertyer;
    }
    public String getPropertyPhone() {
        return propertyPhone;
    }

    public void setPropertyPhone(String propertyPhone) {
        this.propertyPhone = propertyPhone;
    }
    public String getStreeter() {
        return streeter;
    }

    public void setStreeter(String streeter) {
        this.streeter = streeter;
    }
    public String getStreetPhone() {
        return streetPhone;
    }

    public void setStreetPhone(String streetPhone) {
        this.streetPhone = streetPhone;
    }
    public String getCommunityer() {
        return communityer;
    }

    public void setCommunityer(String communityer) {
        this.communityer = communityer;
    }
    public String getCommunityPhone() {
        return communityPhone;
    }

    public void setCommunityPhone(String communityPhone) {
        this.communityPhone = communityPhone;
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
        return "EpidemicHistory{" +
            "epidemicId=" + epidemicId +
            ", streetName=" + streetName +
            ", communityName=" + communityName +
            ", chinaName=" + chinaName +
            ", sex=" + sex +
            ", age=" + age +
            ", cardId=" + cardId +
            ", mobile=" + mobile +
            ", keyArea=" + keyArea +
            ", sojournHistory=" + sojournHistory +
            ", sojournProvince=" + sojournProvince +
            ", sojournCity=" + sojournCity +
            ", sojournDistrict=" + sojournDistrict +
            ", sojournAddress=" + sojournAddress +
            ", sojournLevel=" + sojournLevel +
            ", traffic=" + traffic +
            ", setoutTime=" + setoutTime +
            ", arriveTime=" + arriveTime +
            ", managerTime=" + managerTime +
            ", censusRegister=" + censusRegister +
            ", arriveAddress=" + arriveAddress +
            ", testingTime=" + testingTime +
            ", testingState=" + testingState +
            ", isolateState=" + isolateState +
            ", startTime=" + startTime +
            ", communityPerson=" + communityPerson +
            ", communityMobile=" + communityMobile +
            ", communityWork=" + communityWork +
            ", medicalPerson=" + medicalPerson +
            ", medicalMobile=" + medicalMobile +
            ", medicalWork=" + medicalWork +
            ", propertyer=" + propertyer +
            ", propertyPhone=" + propertyPhone +
            ", streeter=" + streeter +
            ", streetPhone=" + streetPhone +
            ", communityer=" + communityer +
            ", communityPhone=" + communityPhone +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", modifyTime=" + modifyTime +
            ", modifyBy=" + modifyBy +
        "}";
    }
}
