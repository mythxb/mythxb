package com.common.utils;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 *
 * @param <T>数据类型的泛性类
 * 用于返回layui数据
 */
@Repository("data")
public class DataTableResult<T> {
	/**
	 * 状态码（默认为0）
	 */
	private Integer code =0;
	/**
	 * 后台return数据错误时，前端页面显示的错误信息
	 */
	private String msg;
	/**
	 * 	数据个数和
	 */
	private int count;
	/**
	 * 数据对象
	 */
	private List<T> data;

	/**
	 * 传递一个值
	 * */
	private String strvalue;

	/**传递值得容器*/
	private String oprole;
	/**用于存储用户的名称*/
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOprole() {
		return oprole;
	}

	public void setOprole(String oprole) {
		this.oprole = oprole;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStrvalue() {
		return strvalue;
	}

	public void setStrvalue(String strvalue) {
		this.strvalue = strvalue;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}

    public static<T> DataTableResult ok(List<T> list) {
	    DataTableResult result = new DataTableResult();
	    result.setCount(list.size());
	    result.setData(list);
	    return result;
    }
	public static<T> DataTableResult ok(List<T> list, Integer count) {
		DataTableResult result = new DataTableResult();
		result.setCount(count);
		result.setData(list);
		return result;
	}
    public static DataTableResult error(String msg){
	    DataTableResult result = new DataTableResult();
	    result.setCode(400);
	    result.setMsg(msg);
	    return result;
    }
}
