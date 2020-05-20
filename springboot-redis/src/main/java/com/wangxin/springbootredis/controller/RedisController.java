package com.wangxin.springbootredis.controller;

import com.wangxin.springbootredis.entity.User;
import com.wangxin.springbootredis.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/saveUser")
    public String saveUser(){
        User user = new User(2l,"zhangsanfeng","1223333","111111111@163.com");
        userServiceImpl.saveUser(user);
        return "ok";
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id){
        User user = userServiceImpl.findUser(id);
        return user;
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable("id") Long id){
       userServiceImpl.delUser(id);
        return "ok";
    }
}
