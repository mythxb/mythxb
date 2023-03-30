package com.common.utils;

import com.common.utils.httpClient.WebUtilsOld;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于https请求的工具类(安全的http)
 * @author 李熠
 *
 */
@SuppressWarnings("deprecation")
public class SecureWebUtils {

	private static HttpClient httpClient;
	
	static{
		httpClient = new DefaultHttpClient();
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
		secure();
		return WebUtilsOld.doPost(requestUrl, params, connectTimeout, readTimeout, headers);
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
	public static String doPost(String requestUrl,String postData,int connectTimeout,int readTimeout,Map<String,String> headers)throws Exception{
		secure();
		return WebUtilsOld.doPost(requestUrl, postData, connectTimeout, readTimeout, headers);
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
		secure();
		return WebUtilsOld.doUpload(requestUrl, params, connectTimeout, readTimeout, headers);
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
		secure();
		return WebUtilsOld.doGet(requestUrl, params, connectTimeout, readTimeout, headers);
	}
	
	/**
	 * https的安全验证
	 * @throws Exception
	 */
	private static void secure() throws Exception{
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};
		SSLContext ctx = SSLContext.getInstance("SSL");
		// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
		ctx.init(null, new TrustManager[] { xtm }, null);
		SSLSocketFactory sf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, sf);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
	}
	
	public static void main(String[] args) throws Exception{
		String response = WebUtilsOld.doUpload("http://localhost:8080", new HashMap<String, String>(), 10 * 1000, 10 * 1000, new HashMap<String, String>());
		System.out.println(response);
	}
}
