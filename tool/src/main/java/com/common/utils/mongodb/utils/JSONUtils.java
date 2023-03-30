package com.common.utils.mongodb.utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONUtils {
	
	/**
	 * json 字符串转map
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> jsonStrtoMap(String jsonStr)throws Exception{
		JSONObject jsonObject = new JSONObject(jsonStr);
        
        Map<String, String> result = new HashMap<String, String>();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;
	}
	
	public static void main(String[] args) {
		try {
			String json = "{\"name\":\"myth\",\"title\":\"test\"}";
			System.out.println(json);
			Map<String, String> map = JSONUtils.jsonStrtoMap(json);
			System.out.println(map);
			System.out.println(map.get("name"));
			System.out.println(map.get("title"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
