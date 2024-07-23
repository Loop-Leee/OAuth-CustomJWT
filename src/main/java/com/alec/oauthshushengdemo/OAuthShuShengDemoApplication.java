package com.alec.oauthshushengdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.alec.oauthshushengdemo.mapper")
public class OAuthShuShengDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthShuShengDemoApplication.class, args);
    }

}
