package com.common.utils.jpush;


import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.common.utils.encryption.MD5;

import java.util.HashMap;
import java.util.Map;

public class Jpush {

    private static String APPKEY = "f44de284f40c9eceff4799f1";
    private static String MASTER_SECRET = "a56fc4ddbd7c155ae464fac2";

    private static String APPKEYGOV = "2b3cc16dad195dd3de513f0b";
    private static String MASTER_SECRETGOV = "c85241981407c19616a43557";


    /**
     * 推送消息
     * @param alias
     * @param content
     * @param extras
     * @throws Exception
     */
    public static void sendNotifications(String alias,String content,Map<String,String> extras )throws Exception{
//        extras.put("type","1");//跳转到系统消息列表
        //推送消息 Android ios
        Jpush.sendAllNotifications(alias,content,extras);
    }


    /**
     * 推送硬件消息
     * 硬件消息 附件字段 加一个 type -> 2 表示跳转到硬件消息列表
     * @param alias
     * @param content
     * @param extras
     * @throws Exception
     */
    public static void sendBoxNotifications(String alias,String content,Map<String,String> extras )throws Exception{
        extras.put("type","2");//跳转到硬件消息列表
        //推送消息 Android ios
        Jpush.sendAllNotifications(alias,content,extras);
    }


    /**
     * 推送安卓和ios端消息
     * @param alias 过个逗号隔开
     * @param content
     * @param extras
     * @throws Exception
     */
    public static void sendAllNotifications(String alias,String content,Map<String,String> extras )throws Exception{
        String [] aliaes = alias.split(",");
        if(null != aliaes && aliaes.length > 0){
            PushPayload payload = Jpush.buildPushObjectAndroidAndIos(aliaes,content,extras);
            JPushClient jPushClient = new JPushClient(MASTER_SECRET,APPKEY);

            PushResult result = jPushClient.sendPush(payload);
            System.out.println("result -> "+result);
        }
    }


    /**
     * 推送安卓和ios端消息
     * @param alias 过个逗号隔开
     * @param content
     * @param extras
     * @throws Exception
     */
    public static void sendGovAllNotifications(String alias,String content,Map<String,String> extras )throws Exception{
        String [] aliaes = alias.split(",");
        if(null != aliaes && aliaes.length > 0){
            PushPayload payload = Jpush.buildPushObjectAndroidAndIos(aliaes,content,extras);
            JPushClient jPushClient = new JPushClient(MASTER_SECRETGOV,APPKEYGOV);

            PushResult result = jPushClient.sendPush(payload);
            System.out.println("result -> "+result);
        }
    }

    /**
     * 全局推送
     * @param content
     * @throws Exception
     */
    public static void sendAll(String content)throws Exception{
        Map<String,String> extras = new HashMap<String,String>();
//        extras.put("type","1");//类型  1:系统消息 2：硬件消息
        PushPayload payload = Jpush.sendAllAndroidAndIos(content,extras);
        JPushClient jPushClient = new JPushClient(MASTER_SECRET,APPKEY);

        PushResult result = jPushClient.sendPush(payload);
        System.out.println("result -> "+result);
    }

    private static PushPayload sendAllAndroidAndIos(String alert,Map<String,String> extras) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle("新都安全")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extras)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(alert)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .build();
        return payload;
    }


    /**
     * Android ios 推送
     * @param alert 推送内容
     * @param extras 扩展字段
     * @param alias 别名
     * @return
     */
    private static PushPayload buildPushObjectAndroidAndIos(String [] alias,String alert,Map<String,String> extras) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle("新都安全")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extras)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(alert)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound(sound)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )

                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .build();
        return payload;
    }

    public static void main(String [] args){
        try {
            //
//            String alias = "845159693846110854711994300";
            String alias = "8D35010B-EB9A-40EE-BDEB-CDAE969D554t";
            alias = MD5.md5(alias);
            String content = "【新都安全】啦啦啦啦！11111111111111";
            //msg.wav
            Map<String,String> map = new HashMap<String,String>();
            map.put("type","1");
            Jpush.sendAllNotifications(alias,content,map);
//            Jpush.sendAndroidMessage("845418454191840903766225143");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
