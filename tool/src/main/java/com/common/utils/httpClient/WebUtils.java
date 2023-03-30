package com.common.utils.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * jdk11 网络请求
 */
public class WebUtils {

    /**
     * post 请求
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static String doPost(String url,Map<String,String> params,Map<String,String> headers)throws Exception{
        String responseStr = "";
        if(null != url && url.length() > 0){
            HttpPost httpPost = new HttpPost(url);

            //加入请求头
            if(null != headers && headers.size() > 0){
                for(Map.Entry<String, String> entry : headers.entrySet()){
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            //添加参数
            List<NameValuePair> paramsList = new LinkedList<>();
            if(null != params && params.size() > 0){
                for(Map.Entry<String, String> entry : params.entrySet()){
                    paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            if(null != paramsList && paramsList.size() > 0){
                UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(paramsList, "UTF-8");
                httpPost.setEntity(entityParam);
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            // 执行请求
            response = httpClient.execute(httpPost);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            String entityStr = EntityUtils.toString(entity, "UTF-8");
            responseStr = entityStr;
        }else{
            throw new Exception("url is null");
        }

        return responseStr;
    }

    /**
     * get 请求
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static String doGet(String url,Map<String,String> params,Map<String,String> headers)throws Exception{
        String responseStr = "";
        if(null != url && url.length() > 0){
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            URIBuilder uriBuilder = new URIBuilder(url);

            //添加参数
            List<NameValuePair> paramsList = new LinkedList<>();
            if(null != params && params.size() > 0){
                for(Map.Entry<String, String> entry : params.entrySet()){
                    paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            //参数
            if(null != paramsList && paramsList.size() > 0)
                uriBuilder.setParameters(paramsList);


            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //加入请求头
            if(null != headers && headers.size() > 0){
                for(Map.Entry<String, String> entry : headers.entrySet()){
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }


            // 执行请求
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            responseStr = EntityUtils.toString(entity, "UTF-8");
//            System.out.println("responseStr --- > "+responseStr);
        }else{
            throw new Exception("url is null");
        }

        return responseStr;
    }





}
