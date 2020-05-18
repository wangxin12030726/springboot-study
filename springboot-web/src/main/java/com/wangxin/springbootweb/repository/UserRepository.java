package com.wangxin.springbootweb.repository;

import com.wangxin.springbootweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String userName,String email);
}
