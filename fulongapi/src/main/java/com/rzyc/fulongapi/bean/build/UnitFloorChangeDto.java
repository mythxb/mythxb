package com.rzyc.fulongapi.bean.build;

import com.baomidou.mybatisplus.annotation.TableId;
import com.rzyc.fulongapi.enums.HouseType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("楼层新增或修改")
public class UnitFloorChangeDto {


    @ApiModelProperty("楼层id 传修改 不传新增")
    private String floorId;

    @NotNull(message = "单元不能为空")
    @ApiModelProperty(value = "单元id",required = true)
    private String unitId;

    @NotNull(message = "楼层不能为空")
    @ApiModelProperty(value = "楼层",required = true)
    private Integer floorNumber;

    @ApiModelProperty(value = "户数")
    private Integer householdSize = 0;

    @ApiModelProperty(value = "房屋类型 1.居住 2.商铺")
    private Integer houseType = HouseType.MY_LIVE.getType();

    @ApiModelProperty(value = "居住人数")
    private Integer personNum = 0;

    @ApiModelProperty(value = "燃气灶具数")
    private Integer cookerNum = 0;

    @ApiModelProperty(value = "燃气户表数")
    private Integer usertableNum = 0;

    @ApiModelProperty(value = "热水器数")
    private Integer heaterNum = 0;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(Integer householdSize) {
        this.householdSize = householdSize;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
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
}
