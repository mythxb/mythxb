package com.common.utils;

/**
 * 操作html的工具类
 * @author 李熠
 *
 */
public class HtmlUtils {

	/**
	 * 将内容追加到html中
	 * @param title html的标题（title）
	 * @param content html内容(body)
	 * @return
	 */
	public static String appendHtml(String title,String content){
		StringBuilder builder = new StringBuilder(content.length());
		builder.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\" /><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/><title>");
		builder.append(title);
		builder.append("</title></head><body>");
		if(StringUtils.isNotBlank(content)){
			builder.append(content);
		}
		builder.append("</body></html>");
		return builder.toString();
	}
	
}
