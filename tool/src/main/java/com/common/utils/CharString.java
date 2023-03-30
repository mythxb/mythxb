package com.common.utils;

/**
 * 字符串操作类
 * @author hgd
 *
 */
public class CharString {
	
	/**
	 * 字符串截取
	 * 如果截取的开始长度大于字符串的长度 则返回整个字符串
	 * 在开始长度小于字符串长度的时候，则判断结束长度
	 * 如果结束长度小于字符串长度的话 则返回截取部分 否则返回从开始长度
	 * 到字符串结束
	 * 如果结束长度小于字符串长度 获得的字符串后面加 ...
	 * @param v1
	 * @param start
	 * @param end
	 * @return
	 */
	public static String intercept(String v1,Integer start,Integer end){
		String result = "";
		Integer length = v1.length();
		if(start < length){
			if(length > end){
				result = v1.substring(start,end)+"...";
			}else{
				result = v1.substring(start,length);
			}
		}else{
			result = v1;
		}
		return result;
	}

}
