package com.common.utils.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class Result {
	
	@JSONField(name="code")
	private Integer code;
	
	@JSONField(name="message")
	private String message;
	
	@JSONField(name="data")
	private String data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
