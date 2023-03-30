package com.common.utils.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 接口返回数据
 */
public class Result {

	@ApiModelProperty(value = "接口状态码")
	private int code = Code.SUCCESS.getCode();

	@ApiModelProperty(value = "接口返回说明")
	private String message = Message.SUCCESS;

	public int getCode() {
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

}
