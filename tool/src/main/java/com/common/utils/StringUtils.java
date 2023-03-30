package com.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 专门用于操作字符串的类
 * @author myth
 * @date 2014年9月24日
 * @version 0.1.0
 * @since 0.1.0
 */
public class StringUtils {

	private static char chars[] = {'0','1','2','3','4','5','6','7','8','9'};
	private static int char_length = chars.length;

	/**
	 * <p>
	 * 	isEmpty(null) true<br/>
	 * isEmpty("") true<br/>
	 * isEmpty("null") false<br/>
	 * isEmpty(" ") false<br/>
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return null == str || str.length() == 0;
	}

	/**
	 * <p>
	 * 	isEmpty(null) false<br/>
	 * isEmpty("") false<br/>
	 * isEmpty("null") true<br/>
	 * isEmpty(" ") true<br/>
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

	/**
	 * <p>
	 * 	isEmpty(null) true<br/>
	 * isEmpty("") true<br/>
	 * isEmpty("null") false<br/>
	 * isEmpty(" ") true<br/>
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean  isBlank(String str){
		int strLen;
		if(null == str || (strLen = str.length()) == 0){
			return true;
		}
		for(int i = 0;i < strLen;i++){
			if(!Character.isWhitespace(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 	isEmpty(null) false<br/>
	 * isEmpty("") false<br/>
	 * isEmpty("null") true<br/>
	 * isEmpty(" ") false<br/>
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}

	/**
     * 判断字符是否为中文字符
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
    	Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
          return true;
        }
        return false;
    }

    /**
     * 将第一个字符转换成大写<br/>
     * 如：abc转换后：Abc,1ab转换后:1ab
     * @param str 传入的字符串
     * @return 转换后的字符串
     */
    public static String capitalize(String str){
    	int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }

    /**
     * 生成随机字符串
     * @param length 字符串长度
     * @return
     */
    public static String randomString(int length){
    	StringBuilder builder = new StringBuilder(length);
    	Random random = new Random();
    	for (int i = 0; i < length; i++) {
			builder.append(random.nextInt(char_length));
		}
    	return builder.toString();
    }

    /**
     * 字符串分割返回list
     * @param str 需要分割的字符串
     * @param delimiter 通过该字符串分割
     * @return
     */
    public static List<String> strToList(String str,String delimiter){
    	List<String> results = new ArrayList<String>();
    	if(StringUtils.isNotBlank(str)){
			String [] strs = str.split(delimiter);
			if(null != strs && strs.length > 0){
				for (String s : strs) {
					results.add(s);
				}
			}
		}
    	return results;
    }

	/**
	 * 字符串转utf8
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String strToUtf8(String str)throws Exception{
    	/*try {
			str = new String(str.getBytes("GB2312"),"UTF-8");
			str = URLEncoder.encode (str, "UTF-8" );
		}catch (Exception e){
    		e.printStackTrace();
			str = "";
		}*/
		return str;
	}

	/**
	 * 获取编码格式
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}


		return "";
	}

	/**
	 * 输入数量获取*号字符串
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String getStarStr(Integer length)throws Exception{
		String str = "";
		for (int i = 0;i<length;i++){
			str+= "*";
		}
		return str;
	}

	/**
	 * 替换字符串中的部分字符未*号
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static String replaceStr(String str,Integer start,Integer end)throws Exception{
		StringBuilder sb = new StringBuilder(str);
		if(StringUtils.isNotBlank(str)){

			String replaceStr = "";
			Integer strLenght = str.length();
			if(strLenght > start){
				if(strLenght > end ){
					replaceStr = getStarStr(end-start);
					sb = sb.replace(start-1,end-1,replaceStr);
				}else{
					replaceStr = getStarStr(strLenght-start);
					sb = sb.replace(start-1,strLenght-1,replaceStr);

				}
			}
		}
		return sb.toString();
	}

	/**
	 * 去除空格
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) throws Exception{
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 删除字符串两端的指定字符
	 * @param str
	 * @param c
	 * @return
	 */
	public static String customTrim(String str, char c) {
		char[] chars = str.toCharArray();
		int len = chars.length;
		int st = 0;
		while ( (st < len) && (chars[st] == c) ){
			st ++;
		}
		while ( (st < len) && (chars[len-1] == c) ){
			len --;
		}

		return (st > 0) || (len < chars.length) ? str.substring(st, len) : str;
	}

	private StringUtils(){
		throw new AssertionError();
	}
}
