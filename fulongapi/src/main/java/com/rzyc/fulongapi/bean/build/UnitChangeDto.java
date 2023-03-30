package com.rzyc.fulongapi.bean.build;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("单元新增或修改")
public class UnitChangeDto {

    @ApiModelProperty("单位id 传修改 不传新增")
    private String unitId;

    @ApiModelProperty(value = "管理员名称")
    private String unitManager;

    @ApiModelProperty(value = "管理员电话")
    private String unitManagerContact;

    @NotNull(message = "楼栋不能为空")
    @ApiModelProperty(value = "楼栋id",required = true)
    private String buildingId;

    @NotNull(message = "单元号不能为空")
    @ApiModelProperty(value = "单元号",required = true)
    private Integer unitNumber;

    @NotNull(message = "单元名称不能为空")
    @ApiModelProperty(value = "单元名称",required = true)
    private String unitName;

    @NotNull(message = "楼层数不能为空")
    @ApiModelProperty(value = "楼层数",required = true)
    private Integer floorCount;

    @ApiModelProperty(value = "其他管理员")
    private List<HouseholderDto> householders;

    public List<HouseholderDto> getHouseholders() {
        return householders;
    }

    public void setHouseholders(List<HouseholderDto> householders) {
        this.householders = householders;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }
}
