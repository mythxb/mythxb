package com.common.utils.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台easyui要求格式
 * @author hp
 *
 * @param <T>
 */
public class Pager<T> {

	private Integer pageSize = 10;//每页显示多少条
	
	private Integer page = 1;//当前页
	
	private Long total = 0L;
	
	private List<T> rows = new ArrayList<T>();
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize==null?10:pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize!=null&&pageSize>0){
			this.pageSize = pageSize;
		}
	}

	public Integer getPage() {
		return page==null?1:page;
	}

	public void setPage(Integer page) {
		if(page!=null&&page>0){
			this.page = page;
		}
	}

	public List<T> getRows() {
		return rows;
	}
	
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
