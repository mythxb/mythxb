package com.common.utils.model;

public enum Code {

	//成功
	SUCCESS(1),
	//出错
	ERROR(0),
	//没有数据
	NO_DATA(2),
	//参数异常
	EX_PARAM(3),
	//没有该用户
	NOT_USER(4),
	//密码错误
	PASSWORD_ERROR(5),
	//没有权限
	NOT_AUTH(6),
	//企业数据异常
	NOT_ENT(7),
	//验证码发送失败
	SEND_CODE_ERROR(8),
	//验证码错误
	CODE_ERROT(9),
	//该手机已注册
	REGISTERED(10),
	//验证码已过期
	CODE_TIME_OUT(11),
	//旧密码错误
	OLD_PASSWORD_ERROR(12),
	//账号多地登录
	USER_TOKEN(13),
	//已签到
	SIGNINED(14),
	//签名错误
	SIG_ERROR(15),
	//已加入黑名单
	BLACKED(16),
	//已报名
	JOINED(17),
	//盒子已被绑定
	BOXED(18),
	//不能参加自己发布的约骑
	NOT_JOIN(19),
	//优惠券出错
	COUPON_ERROT(20),
	//已绑定手机号
	HAS_MOBILE(21),
	//已绑定
	HAS_BIND(22),
	//盒子编号错误
	BOXID_ERROR(23),
	//未绑定盒子
	NOT_BIND_BOX(24),
	//删除失败
	DELETE_FAIL(25),
	//报名人数已满
	PERSON(26),
	//已修改过
	CHANGED(27),
	//令牌过期
	TOKEN_EXPIRE(28),
	//已有此账户
	HAS_USER(29),
	//已有此账户
	HAS_STUDENT(30),
	//数据不符合要求
	ERROR_DATA(31);


	private int code;

	private Code(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
