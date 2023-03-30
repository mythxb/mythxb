package com.common.utils.model;

import java.util.ArrayList;
import java.util.List;

public class PagerAdmin<T> {

    private Integer page = 0;

    private Long total = 0l;

    private Long records = 0l;

    private List<T> rows = new ArrayList<T>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
