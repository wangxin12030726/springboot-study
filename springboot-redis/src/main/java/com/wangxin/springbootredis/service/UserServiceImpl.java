package com.wangxin.springbootredis.service;

import com.wangxin.springbootredis.entity.User;
import com.wangxin.springbootredis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 注解的方式使用Redis
 */
@Service
public class UserServiceImpl {


    @Autowired
    private RedisUtil redisUtil;

    /**
     *
     * @param user
     */
    @CachePut(value = "user",key = "#user.id")
    public void saveUser(User user){
        //此处可以添加代码，保存到数据库
    }

    /**
     * @Cacheable 注解，在查询之前，先去缓存中获取数据，如果缓存中没有则去数据库中查询，并放回缓存中
     *    cacheNames/value  缓存的名字
     *    key 缓存的key SPEL表达式写法
     *    keyGenerator key的生成策略，和key只能写一个，
     *    cacheManager 缓存管理器
     *    condition 满足条件放入缓存
     *    unless 满足条件不存放缓存
     *    sync 异步执行
     * @param id
     * @return
     */
    @Cacheable(value="user",key="#id")
    public User getUserById(Long id){
        User user = (User)redisUtil.get("user::1");
        return user;
    }

    @Cacheable(value="user", key="#id")
    public User findUser(Long id) {
        User user = new User();
        user.setUserName("hlhdidi");
        user.setPassWord("123");
        user.setId(id.longValue());
        boolean b = redisUtil.hasKey("user::1");
        System.out.println(b);
        return user;
    }

    /**
     * 从缓存中删除数据
     * allEntries 删除 value 缓存中的所有值
     * beforeInvocation 在方法执行之前，先删除缓存，在执行方法，如果方法报错，则已删除的数据不能回复到缓存中
     * @param id
     */
    @CacheEvict(value="user", key="#id")
    public void delUser(Long id) {
        // 删除user
        System.out.println("user删除");
    }
}
