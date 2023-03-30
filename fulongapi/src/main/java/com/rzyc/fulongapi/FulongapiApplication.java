package com.rzyc.fulongapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@SpringBootApplication(scanBasePackages = "com")
@MapperScan("com.rzyc.fulongapi.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class FulongapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FulongapiApplication.class, args);
    }

}
