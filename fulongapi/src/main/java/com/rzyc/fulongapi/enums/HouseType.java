package com.rzyc.fulongapi.enums;

/**
 * 房屋类型： 1、自住 2、出租
 */
public enum HouseType {

    //
    MY_LIVE(1),
    //
    LEASE(2);

    private Integer type;

    HouseType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
