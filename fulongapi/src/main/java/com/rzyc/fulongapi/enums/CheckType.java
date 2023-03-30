package com.rzyc.fulongapi.enums;

/**
 * 检查类型：1、入户检查 2、公共区域检查 3、企业检查 4:特殊建筑
 */
public enum CheckType {

    HOUSE(1),
    COMMON(2),
    ENT(3),
    SPECIAL_BUILD(4);

    private Integer type;

    CheckType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
