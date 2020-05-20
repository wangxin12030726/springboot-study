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

    @CachePut(value = "user",key = "#user.id")
    public void saveUser(User user){
        //此处可以添加代码，保存到数据库
    }

//    @Cacheable(value="user",key="#id")
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

    @CacheEvict(value="user", key="#id")
    public void delUser(Long id) {
        // 删除user
        System.out.println("user删除");
    }
}
