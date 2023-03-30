package com.common.utils.weixin;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisXml {

	@SuppressWarnings("unchecked")
	   public static Map<String, String> parseXml(String content) throws Exception {  
	        // 将解析结果存储在HashMap中  
	        Map<String, String> map = new HashMap<String, String>();   
	        // 从request中取得输入流  
	        // 读取输入流  
	        SAXReader reader = new SAXReader();  
	        Document document = reader.read(content);  
	        // 得到xml根元素  
	        Element root = document.getRootElement();  
	        // 得到根元素的所有子节点  
			List<Element> elementList = root.elements();  
	        // 遍历所有子节点  
	        for (Element e : elementList){
	        	map.put(e.getName(), e.getText());  
	        	System.out.println(e.getName()+":"+e.getText());
	        }
	        // 释放资源  
	        return map;  
	    }  
}
