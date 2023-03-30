package com.common.utils.httpClient;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 用于http请求的工具类
 * @author 李熠
 *
 */
@SuppressWarnings("deprecation")
public class WebUtilsOld {

	private static HttpClient httpClient;
	
	static{
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
	}
	
	/**
	 * 执行post请求
	 * @param requestUrl
	 * @param params
	 * @param connectTimeout
	 * @param readTimeout
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String requestUrl,Map<String,String> params,int connectTimeout,int readTimeout,Map<String,String> headers)throws Exception{
		if(params.containsKey("json")){
			headers.put("Content-Type", "application/json");
		}
		HttpPost post = new HttpPost(requestUrl);
		HttpParams httpParams = post.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if(null != params && params.size() > 0){
			for(Map.Entry<String, String> entry : params.entrySet()){
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		if(params.containsKey("json")){
			StringEntity stringEntity = new StringEntity((String)(params.get("json")), "UTF-8");
			post.setEntity(stringEntity);
		}else{
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams,"UTF-8");
			post.setEntity(formEntity);
		}
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		try {
			if(null != entity){
				String result = EntityUtils.toString(entity,"UTF-8");
				return result;
			}
			return null;
		} finally{
			response = null;
		}
	}

	/**
	 * 执行post请求
	 * @param requestUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String requestUrl,Map<String,String> params)throws Exception{
		int connectTimeout = 20*1000;
		int readTimeout = 20*1000;
		Map<String,String> headers = new HashMap<String,String>();
		if(params.containsKey("json")){
			headers.put("Content-Type", "application/json");
		}
		HttpPost post = new HttpPost(requestUrl);
		HttpParams httpParams = post.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if(null != params && params.size() > 0){
			for(Map.Entry<String, String> entry : params.entrySet()){
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		if(params.containsKey("json")){
			StringEntity stringEntity = new StringEntity((String)(params.get("json")), "UTF-8");
			post.setEntity(stringEntity);
		}else{
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams,"UTF-8");
			post.setEntity(formEntity);
		}
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		try {
			if(null != entity){
				String result = EntityUtils.toString(entity,"UTF-8");
				return result;
			}
			return null;
		} finally{
			response = null;
		}
	}
	
	/**
	 * 执行post请求
	 * @param requestUrl
	 * @param connectTimeout
	 * @param readTimeout
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String requestUrl,String postData,int connectTimeout,int readTimeout,Map<String,String> headers)throws Exception{
		HttpPost post = new HttpPost(requestUrl);
		HttpParams httpParams = post.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		StringEntity stringEntity = new StringEntity(postData, "UTF-8");
		post.setEntity(stringEntity);
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		try {
			if(null != entity){
				return EntityUtils.toString(entity,"UTF-8");
			}
			return null;
		} finally{
			response = null;
		}
	}
	
	/**
	 * 执行带有文件上传的post请求
	 * @param requestUrl
	 * @param params
	 * @param connectTimeout
	 * @param readTimeout
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String doUpload(String requestUrl,Map<String,String> params,int connectTimeout,int readTimeout,Map<String,String> headers)throws Exception{
		HttpPost post = new HttpPost(requestUrl);
		HttpParams httpParams = post.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		MultipartEntity mpEntity = new MultipartEntity();
		if(null != params && params.size() > 0){
			String key,value,prefix;
			for(Map.Entry<String, String> entry : params.entrySet()){
				key = entry.getKey();
				value = entry.getValue();
				//文件流
				if("file".equals(key)){
					String paths[] = value.split(",");
					for (String path : paths) {
						prefix = path.substring(path.lastIndexOf('.'));
						mpEntity.addPart(key,new FileBody(new File(path), UUID.randomUUID().toString()+prefix, "image/jpeg", "UTF-8"));
					}
				}else{
					mpEntity.addPart(key, new StringBody(value,Charset.forName("UTF-8")));
				}
			}
		}
		post.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		try {
			if(null != entity){
				return EntityUtils.toString(entity,"UTF-8");
			}
			return null;
		} finally{
			response = null;
		}
	}
	
	/**
	 * 执行get请求
	 * @param requestUrl
	 * @param params
	 * @param connectTimeout
	 * @param readTimeout
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String requestUrl,Map<String,String> params,int connectTimeout,int readTimeout,Map<String,String> headers)throws Exception{
		if(null != params && params.size() > 0){
			requestUrl = appendParams(requestUrl, params);
		}
		HttpGet get = new HttpGet(requestUrl);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpParams httpParams = get.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		HttpResponse response = httpClient.execute(get);
		try {
			HttpEntity entity = response.getEntity();
			if(null != entity){
				return EntityUtils.toString(entity,"UTF-8");
			}
			return null;
		} finally{
			response = null;
		}
	}


	/**
	 * 执行get请求
	 * @param requestUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String requestUrl,Map<String,String> params)throws Exception{
		int connectTimeout = 20*1000;
		int readTimeout = 20* 1000;
		Map<String,String> headers = new HashMap<String,String>();
		if(null != params && params.size() > 0){
			requestUrl = appendParams(requestUrl, params);
		}
		HttpGet get = new HttpGet(requestUrl);
		if(null != headers && headers.size() > 0){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpParams httpParams = get.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
		HttpResponse response = httpClient.execute(get);
		try {
			HttpEntity entity = response.getEntity();
			if(null != entity){
				return EntityUtils.toString(entity,"UTF-8");
			}
			return null;
		} finally{
			response = null;
		}
	}
	
	/**
	 * delete 格式请求
	 * @param requestUrl
	 * @throws Exception
	 */
	public static String doDelete(String requestUrl)throws Exception{
		HttpDelete delete = new HttpDelete(requestUrl);
		HttpResponse response = httpClient.execute(delete);
		try {
			HttpEntity entity = response.getEntity();
			if(null != entity){
				return EntityUtils.toString(entity,"UTF-8");
			}
			return null;
		} finally{
			response = null;
		}
	}

	public static String sendCPICPost(String requestUrl, Map<String,Object> params) throws Exception {
		String paramsStr = JSONArray.toJSONString(params);
		StringBuffer jsonString;
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(paramsStr);
		writer.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		jsonString = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			jsonString.append(line);
		}
		br.close();
		connection.disconnect();

		return jsonString.toString();
	}
	
	/**
	 * 拼接get请求参数到url之后
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	private static String appendParams(String requestUrl,Map<String,String> params){
		boolean first = true;
		StringBuilder builder = new StringBuilder(requestUrl);
		builder.append('?');
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(first == false){
				builder.append('&');
			}else{
				first = false;
			}
			builder.append(entry.getKey());
			builder.append('=');
			builder.append(entry.getValue());
		}
		return builder.toString();
	}
}
