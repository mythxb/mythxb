package com.common.utils.model;

/**
 * @version 0.1.0
 * @since 0.1.0
 * @param <T>
 */
public class SingleResult<T> extends Result {

	private T data;

	private long count;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
