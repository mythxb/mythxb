package com.rzyc.fulongapi.enums;

/**
 * 风险等级 红：4 橙：3 黄：2 蓝：1
 */
public enum RiskLevel {

    RED(4,"红"),
    ORANGE(3,"橙"),
    YELLOW(2,"黄"),
    BLUE(1,"蓝");

    private Integer level;

    private String levelDesc;

    RiskLevel(Integer level, String levelDesc) {
        this.level = level;
        this.levelDesc = levelDesc;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }
}
