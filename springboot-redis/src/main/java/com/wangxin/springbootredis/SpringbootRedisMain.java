package com.wangxin.springbootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootRedisMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisMain.class,args);
    }
}
