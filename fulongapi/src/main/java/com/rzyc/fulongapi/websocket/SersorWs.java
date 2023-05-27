package com.rzyc.fulongapi.websocket;

import com.rzyc.fulongapi.service.SersorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author dong
 * @date 2022-07-19 13:42
 * @Version V1.0
 */
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/sersorWs/{userId}")
public class SersorWs {

    private static final Logger log = LoggerFactory.getLogger(SersorWs.class);

    //实例一个session，这个session是websocket的session
    public Session session;

    //存放websocket的集合（本次demo不会用到，聊天室的demo会用到）
    public static CopyOnWriteArraySet<SersorWs> webSocketSet = new CopyOnWriteArraySet<>();

    /** 存放所有在线的客户端 key 为用户Id */
    public static Map<String, List<Session>> clientList = new ConcurrentHashMap<>();


    //获取框架信息
    private static ApplicationContext applicationContext;

    //获取注入的信息
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }



    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        System.out.println("onOpen uesrId --- > "+userId);
        this.session = session;

        //一个账户多个客户端
        List<Session> sessions = clientList.get(userId);
        if(null == sessions){
            sessions = new ArrayList<>();
        }
        sessions.add(session);
        clientList.put(userId,sessions);


        log.info("【VitalSigns消息】有新的连接"+userId+", 总数:{}", clientList.size());
        try {
            SersorService sersorService = applicationContext.getBean(SersorService.class);
            //发送呼吸心率传感器数据
            sersorService.sendSersor(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
//        webSocketSet.remove(this);
//        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());

        for (String userId : clientList.keySet()) {
            System.out.println("onClose uesrId --- > "+userId);

            List<Session> sessions = new ArrayList<>();;
            List<Session> sessionsList = clientList.get(userId);
            if(null != sessionsList && sessionsList.size() > 0){
                for (Session sess : sessionsList){
                    if(!sess.equals(session)){
                        sessions.add(sess);
                    }
                }
            }

            if(sessions.size() > 0 ){
                clientList.put(userId,sessions);
            }else{
                clientList.remove(userId);
            }
        }
        log.info("【VitalSigns消息】连接断开, 总数:{}", clientList.size());
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message,@PathParam("userId") String userId) {
        System.out.println("onMessage uesrId --- > "+userId);
        log.info("【VitalSigns消息】收到客户端<"+userId+">发来的消息:{}", message);
        session.getAsyncRemote().sendText("服务端接收到消息！");
    }

    //新增一个方法用于主动向客户端发送消息
    public static void sendMessage(String message) {
        if(null != clientList && clientList.size() > 0){
            for(Map.Entry<String,List<Session>> entry : clientList.entrySet()){
                log.info("【VitalSigns消息】广播消息, message={}", message);
                try {
                    List<Session> sessions = entry.getValue();
                    if(sessions.size() > 0){
                        for (Session sess : sessions){
                            sess.getAsyncRemote().sendText(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        for (WebSocket webSocket: webSocketSet) {
//            log.info("【websocket消息】广播消息, message={}", message);
//            try {
//                webSocket.session.getBasicRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    //新增一个方法用于主动向客户端发送消息
    public static void sendObject(Object message) {
        if(null != clientList && clientList.size() > 0){
            for(Map.Entry<String,List<Session>> entry : clientList.entrySet()){
                log.info("【VitalSigns消息】广播消息, message={}", message);
                try {
                    List<Session> sessions = entry.getValue();
                    if(sessions.size() > 0){
                        for (Session sess : sessions){
                            sess.getAsyncRemote().sendObject(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向指定用户发送消息
     * @version v1.0
     * @author dong
     * @date 2023/3/4 20:10
     */
    public static void sendMessage(String message,String uesrId) throws Exception{
        System.out.println("sendMessage uesrId --- > "+uesrId);
        if(null != clientList && clientList.size() > 0){
            if(clientList.containsKey(uesrId)){
                log.info("【VitalSigns消息】用户消息:"+uesrId+", message={}", message);
                List<Session> sessions = clientList.get(uesrId);
                if(sessions.size() > 0){
                    for (Session sess : sessions){
                        sess.getAsyncRemote().sendText(message);
                    }
                }
            }else{
                log.info("【VitalSigns消息】该用户未登录");
            }
        }

    }


//    public static void sendMessageV1(String message,String uesrId) throws Exception{
//        System.out.println("sendMessage uesrId --- > "+uesrId);
//        if(null != clients && clients.size() > 0){
//            if(clients.containsKey(uesrId)){
//                List<Session> sessions = clientList.get(uesrId);
//                log.info("【VitalSigns消息】用户消息:"+uesrId+", message={}", message);
//                Session session = clients.get(uesrId);
//                session.getAsyncRemote().sendText(message);
//            }else{
//                log.info("【VitalSigns消息】该用户未登录");
//            }
//        }
//
//    }
}
