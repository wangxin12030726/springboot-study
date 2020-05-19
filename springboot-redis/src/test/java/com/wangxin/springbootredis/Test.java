package com.wangxin.springbootredis;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @org.junit.Test
   public void test(){
       stringRedisTemplate.opsForValue().set("key1","testvalue1");
        Assert.assertEquals("testvalue1",stringRedisTemplate.opsForValue().get("key1"));
   }
}

