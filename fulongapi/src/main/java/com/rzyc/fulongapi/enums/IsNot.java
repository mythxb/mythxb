package com.rzyc.fulongapi.enums;

/**
 * 是否状态 1：是 2：否
 */
public enum IsNot {

    IS(1),
    NOT(2);

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    IsNot(Integer state) {
        this.state = state;
    }
}
