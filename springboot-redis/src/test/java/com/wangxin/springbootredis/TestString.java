package com.wangxin.springbootredis;

import com.wangxin.springbootredis.entity.User;
import com.wangxin.springbootredis.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * 测试redis的String类型的数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestString {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //测试set 一个String类型
    @Test
   public void testSet(){
       stringRedisTemplate.opsForValue().set("key1","testvalue1");
        stringRedisTemplate.expire("key1",5, TimeUnit.SECONDS);
        Assert.assertEquals("testvalue1",stringRedisTemplate.opsForValue().get("key1"));
   }

    @Test
   public void testRedisString(){
        User user = new User("wangxintest","123456","111111@163.com");
        //把user放入缓存中
         redisUtil.set("user1", user);
        //把user2放入缓存中，并设置user2的超时时间为10秒
        User user2 = new User("wangxintest2","123456","222222@163.com");
         redisUtil.set("user2", user2, 10);
        //获取user
        System.out.println(redisUtil.get("user1"));
        //获取user2
        System.out.println(redisUtil.get("user2"));
    }

    //使用的incr incrby  decr decrby
    @Test
    public void testIncr(){
        redisUtil.set("user1_friend",1);
        redisUtil.incr("user1_friend",1);
        System.out.println(redisUtil.get("user1_friend"));
        redisUtil.incr("user1_friend",1);
        redisUtil.decr("user1_friend",1);
        System.out.println(redisUtil.get("user1_friend"));
    }



}

