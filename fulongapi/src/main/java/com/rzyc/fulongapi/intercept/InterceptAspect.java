package com.rzyc.fulongapi.intercept;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.model.MultiResult;
import com.common.utils.model.SingleResult;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
@Order(2)
//日志打印
public class InterceptAspect {

    @Pointcut("execution(* com.rzyc.fulongapi.controller.*.*(..))")   //两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void excudeService() {
        System.out.println("-------------拦截器--------------------");
    }



    /*拦截操作*/
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String preSession = request.getSession().getId();
        System.out.println("preSession："+preSession);
        String url = request.getRequestURL().toString();
        System.out.println("url - - - - - - - > "+url);
        System.out.println("ip -> "+request.getRemoteAddr());

        Map<String,Object> requestMsg = new HashMap<>();
        Map<String,String[]> paramsMap = request.getParameterMap();
        if(null != paramsMap && paramsMap.size() > 0){
            for (Map.Entry<String,String[]> map : paramsMap.entrySet()){
                System.out.println(map.getKey() + " -> "+request.getParameter(map.getKey()));
                requestMsg.put(map.getKey(),request.getParameter(map.getKey()));
            }
        }
  /*      HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        String cookies = response.getHeader("Set-Cookie");
        response.setHeader("Set-Cookie",cookies+";SameSite=None;");*/
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        if (result instanceof SingleResult) {
            String resultStr = JSONArray.toJSONString(result);
            Map<String, Object> resultMap = (Map)JSONArray.parseObject(resultStr, HashedMap.class);
            Object code = resultMap.get("code");
            System.out.println("code -> " + code + " ; message -> " + resultMap.get("message"));
        } else if (result instanceof MultiResult) {
            String resultStr = JSONArray.toJSONString(result);
            Map<String, Object> resultMap = (Map)JSONArray.parseObject(resultStr, HashedMap.class);
            Object code = resultMap.get("code");
            System.out.println("code -> " + code + " ; message -> " + resultMap.get("message"));
        } else if (result instanceof String) {
            System.out.println("String result ---- > " + result);
        } else {
            System.out.println("other result ---- > " + JSONArray.toJSONString(result));
        }
        return result;
    }

}
