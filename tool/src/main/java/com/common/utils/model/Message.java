package com.common.utils.model;

/**
 * 给客户端返回的message全部写在这里
 * @author hgd
 * @date 2014年9月23日
 * @version 0.1.0
 * @since 0.1.0
 */
public class Message {

	/**
	 * 成功！
	 */
	public static final String SUCCESS = "成功";

	/**
	 * 服务器出现，请稍后再试！
	 */
	public static final String ERROR = "网络繁忙，请稍后再试。";

	/**
	 * 没有数据！
	 */
	public static final String NO_DATA = "没有数据";

	/**
	 * 参数异常！
	 */
	public static final String EX_PARAM = "参数异常";

	/**
	 * 解密错误
	 */
	public static final String DECRYPT_ERROR = "解密错误";

	public static final String PASSWORD_ERROR = "密码错误";

	public static final String NOT_AUTH = "没有权限";

	public static final String CODE_ERROT = "验证码错误";


	public static final String  PASSWORD= "请输入包含数字字母，长度超过6位的密码";
}
