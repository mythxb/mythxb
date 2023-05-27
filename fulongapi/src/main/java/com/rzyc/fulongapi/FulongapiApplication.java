package com.rzyc.fulongapi;

import com.rzyc.fulongapi.websocket.SersorWs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@SpringBootApplication(scanBasePackages = "com")
@MapperScan("com.rzyc.fulongapi.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class FulongapiApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(FulongapiApplication.class, args);
        SersorWs.setApplicationContext(applicationContext);
    }

}
