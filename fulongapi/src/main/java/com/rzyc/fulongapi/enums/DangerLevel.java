package com.rzyc.fulongapi.enums;

/**
 * 隐患级别 1.一般隐患 2.重大隐患
 */
public enum DangerLevel {

    MAJOR(2),
    COMMONLY(1);

    private Integer state;

    DangerLevel(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
