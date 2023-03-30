package com.rzyc.fulongapi.model.importmodels;

import com.rzyc.fulongapi.model.BuildFloor;

import java.util.List;

public class ImportDangerModel {

    private String checkUser;

    private String roomType;

    private Integer direction;

    private Integer buildingNumber;

    private Integer unitNumber;

    private Integer floorNumber;

    private String dangerProblem;

    private String dangerType;

    private BuildFloor buildFloor;

    private List<DangerProblem>dangerProblems;

    public List<DangerProblem> getDangerProblems() {
        return dangerProblems;
    }

    public void setDangerProblems(List<DangerProblem> dangerProblems) {
        this.dangerProblems = dangerProblems;
    }

    public BuildFloor getBuildFloor() {
        return buildFloor;
    }

    public void setBuildFloor(BuildFloor buildFloor) {
        this.buildFloor = buildFloor;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getDangerProblem() {
        return dangerProblem;
    }

    public void setDangerProblem(String dangerProblem) {
        this.dangerProblem = dangerProblem;
    }

    public String getDangerType() {
        return dangerType;
    }

    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }
}
