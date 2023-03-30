package com.common.utils.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台easyui要求格式
 * @author hp
 *
 * @param <T>
 */
@ApiModel(value = "分页")
public class Pager<T>{



	@ApiModelProperty(value = "每页显示多少条")
	private Integer pageSize = 10;//每页显示多少条

	@ApiModelProperty(value = "当前页")
	private Integer page = 1;//当前页

	@ApiModelProperty(value = "总条数")
	private Long total = 0L;

	@ApiModelProperty(value = "数据列表")
	private List<T> rows = new ArrayList<T>();

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

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

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
