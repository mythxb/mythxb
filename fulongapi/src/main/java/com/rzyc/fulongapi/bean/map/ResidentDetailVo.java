package com.rzyc.fulongapi.bean.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("小区住房信息分页列表")
public class ResidentDetailVo {
    @ApiModelProperty("建筑体名称")
    private String buildName;

    @ApiModelProperty("单元名称")
    private String unitName;

    @ApiModelProperty("单元管理员名称")
    private String unitManager;

    @ApiModelProperty("单元管理员电话")
    private String unitManagerContact;

    @ApiModelProperty("楼栋长")
    private String buildingManager;

    @ApiModelProperty("building_manager_contact")
    private String buildingManagerContact;

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(String unitManager) {
        this.unitManager = unitManager;
    }

    public String getUnitManagerContact() {
        return unitManagerContact;
    }

    public void setUnitManagerContact(String unitManagerContact) {
        this.unitManagerContact = unitManagerContact;
    }

    public String getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(String buildingManager) {
        this.buildingManager = buildingManager;
    }

    public String getBuildingManagerContact() {
        return buildingManagerContact;
    }

    public void setBuildingManagerContact(String buildingManagerContact) {
        this.buildingManagerContact = buildingManagerContact;
    }
}
