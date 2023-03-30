package com.rzyc.fulongapi.enums;

/**
 * 1.未整改 2.整改中 3.已整改 4.无法整改
 */
public enum DangerState {

    NOT_RECTIFY(1),
    RECTIFYING(2),
    RECTIFYED(3),
    UNABLE_RECTIFY(4);


    private Integer state;

    DangerState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
