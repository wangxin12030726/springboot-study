package com.wangxin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        return "hello "+ name;
    }
}
