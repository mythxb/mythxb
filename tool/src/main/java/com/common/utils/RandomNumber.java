package com.common.utils;

import java.util.UUID;

/**
 * 生成用户认证码和随机数字
 *@作者	饶正勇
 *@创建日期	2009-12-4
 *@版本	2.0
 */
public class RandomNumber
{
	/**
	 * 生成4位用户认证码
	 * @return long sRand 用户认证码
	 * */
	public static long randomFourNumber(){
		long sRand = (long)Math.round(Math.random()*(8999)+1000);
		return sRand;
	}
	/**
	 * 生成6位用户认证码
	 * @return long sRand 用户认证码
	 * */
	public static long randomNumber(){
		long sRand = (long)Math.round(Math.random()*(899999)+100000);
		return sRand;
	}
	/**
	 * 生成8位随机数字
	 * @return long sRand 8位随机数字
	 * */
	public static long getRandomNumber()
	{
		long sRand = (long)Math.round(Math.random()*(89999999)+10000000);
		return sRand;
	}
	/**
	 * 生成字符型主键
	 * @return uuid 字符型主键
	 * 	 */
	public static String randomUUidPK() {
		/**
		 * 定义一个固定值9999999999999L
		 * 用这个时间减去现在的时间戳，这样得到的一个值是按时间递减的，
		 * 那么在数据库插入uuid的时候，uuid按排列顺序是排在mysql的最前面的
		 * 主要目的是不用按时间顺序排序，查询数据库
		 */
		long fd=9999999999999L;//定义固定值
		long g=fd-System.currentTimeMillis();
		try {
			return String.valueOf(g+""+randomNumber()+getRandomNumber());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 生成一个易信id
	 * 用时间戳 加上一个4位随机数 
	 * 在加上一个8位随机数
	 * @return
	 */
	public static String getAccid(){
		String result = "";
		try {
			result = System.currentTimeMillis()+""+randomFourNumber()+getRandomNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getUUid(){
		String result = "";
		result = UUID.randomUUID().toString();
		return result;
	}

	/**
	 * 生成手机验证码
	 * @return
	 * @throws Exception
	 */
	public static String getMobileCode() throws Exception {
		Integer num = (int) ((Math.random() * 9 + 1) * 100000);
		return num + "";
	}

	/**
	 * 生成一个4为随机数加时间戳的订单号
	 * @return
	 */
	public static String getOrderNum(){
		return randomFourNumber() + "" + System.currentTimeMillis() + "";
	}
	
	
}


