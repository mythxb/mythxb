package com.rzyc.fulongapi.intercept.log;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.jwt.JwtUtil;
import com.common.utils.model.Result;
import com.rzyc.fulongapi.mapper.LogMapper;
import com.rzyc.fulongapi.model.Log;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录存储
 */
@Component
@Aspect
@Order(2)
public class LogAspect {

    protected final static Logger logger = LoggerFactory.getLogger("Aspect ->  ");

    //日志
    @Autowired
    protected LogMapper logMapper;

    /**
     * 拦截位置
     */
    @Pointcut("execution(* com.rzyc.fulongapi.controller..*.*(..))")
    public void saveLog() {}

    //用around得到方法使用的时间
    @Around(value = "saveLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        System.out.println("");
        System.out.println("uri -> "+request.getRequestURI());
        System.out.println("ip -> "+getIpAddr(request));

        //获取参数字符串
        String requestStr = "";

        String requestBodyStr = "";

            Map<String,Object> requestMsg = new HashMap<>();
            Map<String,String[]> paramsMap = request.getParameterMap();
            if(null != paramsMap && paramsMap.size() > 0){
                for (Map.Entry<String,String[]> map : paramsMap.entrySet()){
                    requestMsg.put(map.getKey(),request.getParameter(map.getKey()));
                }
            }
            requestStr = JSONArray.toJSONString(requestMsg);


            Object[] args = joinPoint.getArgs();
            Object[] arguments = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                    continue;
                }
                arguments[i] = args[i];
            }

            if (arguments != null) {
                try {
                    requestBodyStr = JSONArray.toJSONString(arguments);
                } catch (Exception e) {
                    requestBodyStr = arguments.toString();
                }
            }


        System.out.println("requestStr -> "+requestStr);
        System.out.println("requestBodyStr -> "+requestBodyStr);

        //获取返回值
        Object obj=joinPoint.proceed();
        Map<String,Object> responseMap = new HashMap<>();
        if (obj instanceof Result) {
            String resultStr = JSONArray.toJSONString(obj);
            Map<String, Object> resultMap = (Map)JSONArray.parseObject(resultStr, HashedMap.class);
            Object code = resultMap.get("code");
            responseMap.put("code",code);
            responseMap.put("message",resultMap.get("message"));
        } else if (obj instanceof String) {
            System.out.println("String result ---- > " + obj);
            responseMap.put("strmessage",obj);
        } else {
            responseMap.put("othermessage",JSONArray.toJSONString(obj));
        }
        System.out.println("responseMap -> "+JSONArray.toJSONString(responseMap));

        //获取用户id
        String userId = "";
        String userToken = request.getHeader("userToken");
        if(StringUtils.isNotBlank(userToken)){
            userToken = userToken.replaceAll("Bearer ","");
            userId = JwtUtil.getTokenMsg(userToken);
        }


        /* 保存日志 start */
        Log logs = new Log();
        logs.setId(RandomNumber.getUUid());
        logs.setCreateTime(new Date());
        logs.setUserId(userId);
        logs.setParameter(requestStr+"->"+requestBodyStr);
        logs.setResponseStr(JSONArray.toJSONString(responseMap));
        logs.setIp(getIpAddr(request));
        logs.setBehavior(request.getRequestURI());
        saveLog saveLog = new saveLog(logs);
        Thread thread = new Thread(saveLog);
        thread.start();
        /* 保存日志 start */

        return obj;
    }

    /**
     * 异步保存日志
     */
    class saveLog implements Runnable{

        private Log logs;

        public saveLog(Log logs) {
            this.logs = logs;
        }

        //默认构造方法
        public saveLog() {
        }

        @Override
        public void run() {
            try {
                this.saveLogs(logs);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 保存日志
         * @param logs
         * @throws Exception
         */
        public void saveLogs(Log logs)throws Exception{
            logMapper.insert(logs);
        }

    }



    /**
     * 获取ip地址
     * @author: hanguodong
     * @date: 2023/6/6 21:57
     * @param: [request]
     * @return:
     **/
    public String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");

        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {

            return ip;

        }

        ip = request.getHeader("X-Forwarded-For");

        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {

            int index = ip.indexOf(',');

            if (index != -1) {

                return ip.substring(0, index);

            } else {

                return ip;

            }

        } else {

            return request.getRemoteAddr();

        }

    }


}
