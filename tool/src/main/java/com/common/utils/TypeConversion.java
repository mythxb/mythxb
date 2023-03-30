package com.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 类型装换类
 * 类型之间的转换
 * @author hgd
 *
 */
public class TypeConversion {
	
	/**
	 * String 类型 装换成 Long 类型
	 * @param v1
	 * @return
	 */
	public static Long StringToLong(String v1){
		Long result = 0l;
		if(StringUtils.isNotBlank(v1)){
			result = Long.valueOf(v1);
		}
		return result;
	}
	
	/**
	 * String 类型 装换成 Integer 类型
	 * @param v1
	 * @return
	 */
	public static Integer StringToInteger(String v1){
		Integer result = 0;
		if(StringUtils.isNotBlank(v1)){
			result = Integer.valueOf(v1);
		}
		return result;
	}
	
	/**
	 *  String 类型 装换成 Double 类型
	 * @param v1
	 * @return
	 */
	public static Double StringToDouble(String v1){
		Double result = 0.0;
		if(StringUtils.isNotBlank(v1)){
			result = Double.valueOf(v1);
		}
		return result;
	}
	
	/**
	 * Timestamp 转 String
	 * @param t1
	 * @param patten
	 * @return
	 */
	public static String timestampToString(Timestamp t1,String patten){
	     DateFormat sdf = new SimpleDateFormat(patten);   
	     return sdf.format(t1);   
	}
	
	/**
	 * InputStream 转 byte []数组
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte [] inputStreamToByte(InputStream in)throws Exception{
		 ByteArrayOutputStream output = new ByteArrayOutputStream();
		 byte[] buffer = new byte[4096];
		 int n = 0;
		 while (-1 != (n = in.read(buffer))) {
		   output.write(buffer, 0, n);
		 }
		 return output.toByteArray();
	}
	
	/**
	 * Url 转 InputStream
	 * @param fileUrl
	 * @param is
	 * @throws Exception
	 */
	public static InputStream urlToInputStream(String fileUrl,InputStream is)throws Exception{
		URL  url = new URL(fileUrl); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.  
		System.out.println(conn.getContentLength());;
        conn.connect();  
        is = conn.getInputStream(); //得到网络返回的输入流  
        return is;
	}
	
	/**
	 * 格式化小数  
	 * @param decimal 要格式化的小数
	 * @param num 保留到小数点后多少位
	 * @return
	 * @throws Exception
	 */
	public static Double decimalFormat(Double decimal,Integer num)throws Exception{
		BigDecimal b = new BigDecimal(decimal);
		decimal = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		return decimal;
	}

	/**
	 * 处理字符串 在字符串前面和后面加 % 号
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String getCondition(String condition) {
		if (StringUtils.isNotBlank(condition)) {
			condition = condition.replace(" ","");
			condition = condition.replace("\t","");
			condition = "%" + condition + "%";
		} else {
			condition = "%%";
		}
		return condition;
	}

	/**

	 - listToTree
	 - <p>方法说明<p>
	 - 将JSONArray数组转为树状结构
	 - @param arr 需要转化的数据
	 - @param id 数据唯一的标识键值
	 - @param pid 父id唯一标识键值
	 - @param child 子节点键值
	 - @return JSONArray
	 */
	public static JSONArray listToTree(JSONArray arr, String id, String pid, String child){
		JSONArray r = new JSONArray();
		JSONObject hash = new JSONObject();
		//将数组转为Object的形式，key为数组中的id
		for(int i=0;i<arr.size();i++){
			JSONObject json = (JSONObject) arr.get(i);
			hash.put(json.getString(id), json);
		}
		//遍历结果集
		for(int j=0;j<arr.size();j++){
			//单条记录
			JSONObject aVal = (JSONObject) arr.get(j);
			//在hash中取出key为单条记录中pid的值
			JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
			//如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
			if(hashVP!=null){
				//检查是否有child属性
				if(hashVP.get(child)!=null){
					JSONArray ch = (JSONArray) hashVP.get(child);
					ch.add(aVal);
					hashVP.put(child, ch);
				}else{
					JSONArray ch = new JSONArray();
					ch.add(aVal);
					hashVP.put(child, ch);
				}
			}else{
				r.add(aVal);
			}
		}
		return r;
	}

}
