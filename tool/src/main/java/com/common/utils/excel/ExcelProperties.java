package com.common.utils.excel;

public class ExcelProperties {

    private Integer second;

    private String name;

    private String path;

    public ExcelProperties(Integer minutes, String name, String path) {
        this.second = minutes;
        this.name = name;
        this.path = path;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
