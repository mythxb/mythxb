package com.rzyc.fulongapi.intercept;

import com.common.utils.StringUtils;
import com.common.utils.jwt.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器
 */
@Aspect
@Component
@Order(1)
public class LoginAspect {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    /**
     * 拦截位置
     */
    @Pointcut("@annotation(com.rzyc.fulongapi.intercept.LoginAuth)execution(* com.rzyc.fulongapi.controller..*.*(..))")
    public void login() {}

    /**
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("login()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //操作日志
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        logger.info("--------------aop----------------");
        //获取存在header中的用户token
        String userToken = request.getHeader("userToken");
        logger.info("userToken --------- > "+userToken);


        //token验证
        verificationToken(userToken);

        return proceedingJoinPoint.proceed();
    }

    /**
     * 验证token
     * @param userToken
     * @throws Exception
     */
    private void verificationToken(String userToken) throws Exception {
        //token不能为空
        if (StringUtils.isNotBlank(userToken)) {
            String swaggerToken = "rzyc123.";
            //如果是测试token则放行 否则用jwt验证
            if (!swaggerToken.equals(userToken)) {
                userToken = userToken.replaceAll("Bearer ", "");
                if (!JwtUtil.checkToken(userToken)) {
                    throw new TokenException("user token is expire");
                }
            }
        } else {
            throw new TokenException("user token is null");
        }
    }



}
