package com.wangxin.springbootredis.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class InitRedisClockController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    @GetMapping("/initRedis")
    public String initRedisClock(){
        redisTemplate.opsForValue().set("redis_lock",100);
        return "OK";
    }

    @GetMapping("/deductLock")
    public String deductLock(){
        //添加synchronized，使用jmeter压测后发现会有超买的现象，这种在单台的服务器下没有问题，在分布式下有问题
        synchronized (this){
            int value = Integer.parseInt(redisTemplate.opsForValue().get("redis_lock").toString());
            if(value>0){
                int leastValue = value -1;
                redisTemplate.opsForValue().set("redis_lock",leastValue);
                System.out.println("秒杀成功，剩余库存："+leastValue);
            }else{
                System.out.println("秒杀失败，库存不足");
            }
            return "end";
        }

    }
    @GetMapping("/deductLock2")
    public String deductLock2(){
        //使用redis的setnx命令，添加一个分布式锁,并设置一个锁的失效时间，防止死锁的问题，超过时间后redis自动释放该锁
        //这个写有一个问题，就是在高并发下，A线程加的锁会被B线程给释放，
        //此代码问题：A线程获得锁后  执行程序需要15秒，在10秒后  redis会自动释放锁
        //   此时 B线程就会获得锁，然后继续执行，这个A线程完成了任务，则回去释放锁，此时C线程就会进行，
        //在高并发下这种问题是致命的，会导致所有的程序错乱
        //这里可以把锁的超时时间设置长一点，但是，时间多长合适呢？
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("fbs_lock", "wangxin",10, TimeUnit.SECONDS);
            if(!aBoolean){
                System.out.println("没有获取到锁");
                return "error";
            }
            int value = Integer.parseInt(redisTemplate.opsForValue().get("redis_lock").toString());
            if(value>0){
                int leastValue = value -1;
                redisTemplate.opsForValue().set("redis_lock",leastValue);
                System.out.println("秒杀成功，剩余库存："+leastValue);
            }else{
                System.out.println("秒杀失败，库存不足");
            }
            return "end";
        } finally {
            redisTemplate.delete("fbs_lock");
        }
    }

    @GetMapping("/deductLock3")
    public String deductLock3(){
        //添加一个uuid作为唯一标识，判断锁只能由自己释放，不能被其他线程释放
        //此代码问题：A线程获得锁后  执行程序需要15秒，在10秒后  redis会自动释放锁
        //   此时 B线程就会获得锁，但是A线程还没有执行完毕，所以这样还是会在并发执行。
        String uuid = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("fbs_lock", uuid,10, TimeUnit.SECONDS);
            if(!aBoolean){
                System.out.println("没有获取到锁");
                return "error";
            }
            int value = Integer.parseInt(redisTemplate.opsForValue().get("redis_lock").toString());
            if(value>0){
                int leastValue = value -1;
                redisTemplate.opsForValue().set("redis_lock",leastValue);
                System.out.println("秒杀成功，剩余库存："+leastValue);
            }else{
                System.out.println("秒杀失败，库存不足");
            }
            return "end";
        } finally {
            //判断自己的锁只能自己去释放,其他线程不能释放
            if(uuid.equals(redisTemplate.opsForValue().get("fbs_lock"))){
                redisTemplate.delete("fbs_lock");
            }
        }
    }
    @GetMapping("/deductLock4")
    public String deductLock4(){
       //针对上面的问题，使用Redisson解决

        String uuid = UUID.randomUUID().toString();
        RLock lock = redisson.getLock("fbs_lock");
        try {
            lock.lock();
            int value = Integer.parseInt(redisTemplate.opsForValue().get("redis_lock").toString());
            if(value>0){
                int leastValue = value -1;
                redisTemplate.opsForValue().set("redis_lock",leastValue);
                System.out.println("秒杀成功，剩余库存："+leastValue);
            }else{
                System.out.println("秒杀失败，库存不足");
            }
            return "end";
        } finally {
            lock.unlock();
        }
    }

}
