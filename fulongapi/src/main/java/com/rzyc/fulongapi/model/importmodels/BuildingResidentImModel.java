package com.rzyc.fulongapi.model.importmodels;

public class BuildingResidentImModel {

    private  String street;

    private  Integer direction;

    private  Integer buildingNumber;

    private  Integer buildingUnitNumber;

    private  Integer buildingFloorNumber;

    private  String unitManagerName;

    private String unitManagerPhone;

    private Integer RoomUsingType;

    private String residentName;

    private String residentPhone;

    private String identifyCode;

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Integer getBuildingUnitNumber() {
        return buildingUnitNumber;
    }

    public void setBuildingUnitNumber(Integer buildingUnitNumber) {
        this.buildingUnitNumber = buildingUnitNumber;
    }

    public Integer getBuildingFloorNumber() {
        return buildingFloorNumber;
    }

    public void setBuildingFloorNumber(Integer buildingFloorNumber) {
        this.buildingFloorNumber = buildingFloorNumber;
    }

    public String getUnitManagerName() {
        return unitManagerName;
    }

    public void setUnitManagerName(String unitManagerName) {
        this.unitManagerName = unitManagerName;
    }

    public String getUnitManagerPhone() {
        return unitManagerPhone;
    }

    public void setUnitManagerPhone(String unitManagerPhone) {
        this.unitManagerPhone = unitManagerPhone;
    }

    public Integer getRoomUsingType() {
        return RoomUsingType;
    }

    public void setRoomUsingType(Integer roomUsingType) {
        RoomUsingType = roomUsingType;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getResidentPhone() {
        return residentPhone;
    }

    public void setResidentPhone(String residentPhone) {
        this.residentPhone = residentPhone;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }
}
