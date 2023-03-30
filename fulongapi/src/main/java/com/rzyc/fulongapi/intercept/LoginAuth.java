package com.rzyc.fulongapi.intercept;

import java.lang.annotation.*;

/**
 * 登录拦截
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface LoginAuth {

    String content() default "";

}
