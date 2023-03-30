package com.rzyc.fulongapi.enums;

/**
 * 账号类型 1、政府账号 2、楼栋管理员 3、户主
 */
public enum UserType {

    GOV(1),
    BUILD(2),
    UNIT(3),
    ENT(4);

    private Integer type;

    UserType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
