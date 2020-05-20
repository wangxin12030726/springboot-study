package com.wangxin.springbootredis;

import com.wangxin.springbootredis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试Redis的list类型的数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestList {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testRedisList(){
        redisTemplate.opsForList().leftPush("wangxintestlist",1);
        redisTemplate.opsForList().leftPush("wangxintestlist",2);
        redisTemplate.opsForList().leftPush("wangxintestlist",3);
    }
    //获取list的长度，按照范围查找数据
    @Test
    public void testList(){
        System.out.println("list的长度是：" + redisTemplate.opsForList().size("wangxintestlist"));
        List<Object> wangxintestlist = redisTemplate.opsForList().range("wangxintestlist", 0, -1);
        for(Object obj :wangxintestlist){
            System.out.println(obj);
        }
    }
    /**
     * lpop  blpop rpop brpop
     */
    @Test
    public void testPop(){
        Object leftPop = redisTemplate.opsForList().leftPop("wangxintestlist");
        System.out.println(leftPop);
        Object rightPop = redisTemplate.opsForList().rightPop("wangxintestlist");
        System.out.println(rightPop);
    }
}
