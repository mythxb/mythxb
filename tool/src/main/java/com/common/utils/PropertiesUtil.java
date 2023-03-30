package com.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 操作.properties文件的工具类
 * @author 李熠
 * @date 2014-7-16
 * @company 成都市映潮科技有限公司
 * @version 0.3.0
 * @since 0.2.5
 */
public class PropertiesUtil {	

	private static Properties properties=new Properties();
	
	private static PropertiesUtil propertiesUtil;
	
	private PropertiesUtil(){		
	}
	
	private static void loadFile(String filename){
		try {
			InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/"+filename);
			if(null != inputStream){
				properties.load(inputStream);
			}else{
				throw new IOException(String.format("the filename:%s is not found.", filename));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized PropertiesUtil createPropertiesUtil(String filename){
		if (propertiesUtil==null) {
			propertiesUtil=new PropertiesUtil();
		}
		loadFile(filename);
		return propertiesUtil;
	}	
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}

	public static void main(String[] args) {
		PropertiesUtil propertiesUtil= PropertiesUtil.createPropertiesUtil("config.properties");
		System.out.println(propertiesUtil.getProperty("FILEPATH"));
	}
}
