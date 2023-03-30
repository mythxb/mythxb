package com.common.utils.weChat.util;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.Arith;
import com.common.utils.Constants;
import com.common.utils.SecureWebUtils;
import com.common.utils.StringUtils;
import com.common.utils.weChat.config.Pay;
import com.common.utils.weixin.RequestHandler;
import com.common.utils.weixin.Sha1Util;
import com.common.utils.weixin.TenpayUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WechatPay {
	
	public static Pay placeOrder(String payId, String title, Double totalPrice, String prepayId, HttpServletRequest request, HttpServletResponse response){
		Pay weChat = new Pay();
		try {
			String app_id="wxcfbaf8493e0e7668";
			String app_secret="47de72e074765fe3738f79178c58bf4a";
			String partner="1529391031";
			String partner_key="AmollymollymollymollymollymollyA";
			String currTime = TenpayUtil.getCurrTime();// 8位日期
			String strTime = currTime.substring(8, currTime.length());// 四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";// 10位序列号,可以自行调整。
			String strReq = strTime + strRandom;// 随机数
			String nonce_str = strReq; 
			String out_trade_no = payId;// 商户订单号
			String spbill_create_ip = request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")?"127.0.0.1":request.getRemoteAddr();//下单电脑ip
			System.out.println("客户端ip:"+spbill_create_ip);
			double fen = Arith.mul(totalPrice, 100);
			Integer price = (int)fen;
			String notify_url = Constants.WechatNotifyUrl;
			String trade_type = "APP";//"NATIVE"
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", app_id);
			packageParams.put("mch_id", partner);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body",title);
			packageParams.put("out_trade_no", out_trade_no);
			packageParams.put("total_fee",price+"");
			packageParams.put("spbill_create_ip",spbill_create_ip);
			packageParams.put("notify_url", notify_url);
			packageParams.put("trade_type", trade_type);

			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(app_id,app_secret, partner_key);

			System.out.println("签名字符串 = = = "+JSONArray.toJSONString(packageParams));
			String sign = reqHandler.createSign(packageParams);
//			spbill_create_ip = "171.214.165.89";
			String xml = "<xml>" +
					"<appid>" + app_id + "</appid>" + 
					"<mch_id>"+ partner + "</mch_id>" + 
					"<nonce_str>" + nonce_str+ "</nonce_str>" + 
					"<sign>" + sign + "</sign>" + 
					"<body>"+ title + "</body>"+ 
					"<out_trade_no>"+ out_trade_no+ "</out_trade_no>"+
					"<total_fee>" + price + "</total_fee>" + 
					"<spbill_create_ip>"+ spbill_create_ip + "</spbill_create_ip>" + 
					"<notify_url>"+notify_url+"</notify_url>" + 
					"<trade_type>" + trade_type	+ "</trade_type>"+ 
					"</xml>";
			System.out.println("xml = = "+xml);
//			try {
//				String allParameters = reqHandler.genPackage(packageParams);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String prepay_id = "";
			if(StringUtils.isBlank(prepayId)){
				try {
					Map<String,String> headers = new HashMap<String,String>();
					headers.put("Content-Type","application/json");
					String content = SecureWebUtils.doPost(createOrderURL, xml, 30 * 1000, 30 * 1000, headers);
					if (null!=analysisUniCodeRes(content)) {
						String[] a = content.split("<prepay_id><!\\[CDATA\\[");
						@SuppressWarnings("unused")
						String[] b = a[1].split("\\]\\]></prepay_id>");
						prepay_id = analysisUniCodeRes(content);
					}
					System.out.println("prepayId="+prepay_id);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				prepay_id = prepayId;
			}

			String timestamp = Sha1Util.getTimeStamp();
			String packages = "Sign=WXPay";
			SortedMap<String, String > signMap = new TreeMap<String, String>();
			signMap.put("appid", app_id);
			signMap.put("prepayid", prepay_id);
			signMap.put("partnerid", partner);
			signMap.put("package", packages);
			signMap.put("noncestr", nonce_str);
			signMap.put("timestamp", timestamp);
			sign = reqHandler.createSign(signMap);
			weChat.setAppid(app_id);
			weChat.setNoncestr(nonce_str);
			weChat.setPackages(packages);
			weChat.setPartnerid(partner);
			weChat.setPrepayid(prepay_id);
			weChat.setSign(sign);
			weChat.setTimestamp(timestamp);
			weChat.setKey(partner_key);
			System.out.println("weChat = = = = "+ JSONArray.toJSONString(weChat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weChat;
	}
	
	private static String analysisUniCodeRes(String xml) {
		System.out.println("解析："+xml);
		String result=null;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			System.out.println("根节点：" + rootElt.getName());
			if("SUCCESS".equals(rootElt.elementTextTrim("result_code"))&&"SUCCESS".equals(rootElt.elementTextTrim("return_code"))){
				result=rootElt.elementTextTrim("prepay_id");
			}
		} catch (DocumentException e) {
			System.out.println("解析申请统一支付ID响应失败："+e.getMessage());
		}
		return result;
	}

}
