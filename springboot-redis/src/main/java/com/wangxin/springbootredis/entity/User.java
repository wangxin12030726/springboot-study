package com.wangxin.springbootredis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String passWord;
	private String email;

	public User(){}
	public User(Long id,String userName, String passWord, String email) {
		this.id = id;
		new User(userName,passWord,email);
	}
	public User(String userName, String passWord, String email) {
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", passWord='" + passWord + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}