/**
 * @类功能说明:
 * @描述:
 * @作者:hex
 * @时间:2015-7-25 上午11:09:28
 * @版本:V1.0
 */
package com.common.utils.upload;

import java.io.*;
import java.util.Properties;

public class SystemPropertiesUtil
{
	private static Properties prop;
	private static String path;
	static 
	{   
        prop = new Properties();   
        //获取路径
        path = SystemPropertiesUtil.class.getClassLoader().getResource("config/config.properties").getPath();
//        String pre=System.getProperty("user.dir");  
//        path = pre + File.separator + "src" + File.separator + "SystemInfo.properties";
//        path = path.replace("/", File.separator);
        try
        {   System.out.println(path);
            InputStream in = new FileInputStream(path);
            prop.load(in);   
        } 
        catch (IOException e) 
        {   
            e.printStackTrace();   
        }   
    }   
  
	/**
	 * 根据键值获取内容
	 * @方法功能说明:
	 * @作者:hex
	 * @时间:2015-7-25 上午11:10:58
	 * @param key
	 * @return
	 */
    public static String getValue(String key)
    {
    	return prop.getProperty(key);
    }
    
    public static boolean writeValue(String key, String content)
    {
    	try
    	{
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(path);
            prop.setProperty(key, content);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            prop.store(fos, "Update '" + key + "' value");
            return true;
        }
    	catch (IOException e)
        {        	
            System.err.println("属性文件更新错误");
        }
    	
    	return false;
    }
  
    public static void main(String args[]){   
        writeValue("aboutme", "这里配置多得是仨女的撒");   
        System.out.println(getValue("aboutme"));   
    }   
}
