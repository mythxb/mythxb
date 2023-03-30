package com.rzyc.fulongapi.enums;

public enum ResidentType {
    selfUse(1),
    live(2),
    Sharing(3),
    lease(4),
    Wholerent(5),
    commercial(6),
    ;

    private Integer type;

    ResidentType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
