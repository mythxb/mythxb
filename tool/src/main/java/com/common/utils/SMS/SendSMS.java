package com.common.utils.SMS;

import com.common.utils.httpClient.WebUtilsOld;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送公共类
 */
public class SendSMS {

    /**
     *
     * @param mobile
     * @param content
     * @return
     * @throws Exception
     */
    public static String sendSMS(String mobile,String content)throws Exception{
        String url = "http://120.55.197.77:1210/Services/MsgSend.asmx/SendMsg";
        Map<String,String> params = new HashMap<String,String>();
        params.put("userCode","zmzxcf");
        params.put("userPass","Molead123456");
        params.put("DesNo",mobile);
        params.put("Msg",content);
        params.put("Channel","0");
        String result = WebUtilsOld.doPost(url,params,20*1000,20*1000,new HashMap<String, String>());
        return result;
    }


    public static void main(String [] args){
        try {

            String result = SendSMS.sendSMS("19980425617","【摩砺】尊敬的川A00001骑士：10：20，您的爱车疑似发生故障，请及时检查车辆。");
            System.out.println("result = = = "+result);




            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
