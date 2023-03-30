package com.common.utils.model;

import java.util.List;

/**
 * 多条数据集合
 * @author 李熠
 * @date 2014年9月23日
 * @version 0.1.0
 * @since 0.1.0
 * @param <T>
 */
public class MultiResult<T> extends Result {

	private List<T> data;

	private Integer Count;

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
