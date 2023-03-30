package com.common.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CLEANUI 返回数据类型
 * @param <T>
 */
public class Clean<T> {

    /**
     * 页数
     */
    private Long draw = 1l;

    /**
     * 开始条数
     */
    private Integer start = 0;

    /**
     * 长度
     */
    private Integer length = 10;

    /**
     * 总条数
     */
    private Long recordsTotal = 0l;

    /**
     * 总条数
     */
    private Long recordsFiltered = 0l;

    /**
     * 数据
     */
    private List<T> data = new ArrayList<T>();

    /*公用查询条件 start*/

    /**
     * 开始时间
     */
    private String startTime = "";

    /**
     * 结束时间
     */
    private String endTime = "";

    /**
     * 查询条件
     */
    private String condition = "";

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getDraw() {
        return draw;
    }

    public void setDraw(Long draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
