package com.rzyc.fulongapi.enums;

/**
 *  1.燃气 2.公共 3.消防
 * @version v1.0
 * @author dong
 * @date 2023/3/28 16:56
 */
public enum DangerTypeEnums {

    GAS(1),
    COMMON(2),
    FIRE(3);

    private Integer type;

    DangerTypeEnums(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
