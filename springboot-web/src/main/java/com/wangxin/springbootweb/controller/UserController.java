package com.wangxin.springbootweb.controller;

import com.wangxin.springbootweb.entity.User;
import com.wangxin.springbootweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/get/user/{id}")
    public User getUserById(@PathVariable("id") String id){
        User one = userRepository.getOne(Long.parseLong(id));
        return one;
    }
    @RequestMapping("/get/user/username/{name}")
    public User findByUserName(@PathVariable("name")String name){
        User user = userRepository.findByUserName(name);
        return user;
    }
}
