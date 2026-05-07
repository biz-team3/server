package com.bizteam3.server.user.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	Integer userId;
	String username;
	String password;
	String name;
	String bio;
	String website;
	String profileImg;
	AccountVisType accountVis;
	LocalDateTime createdAt;
	LocalDateTime updateAt;
	LocalDateTime deleteAt;

	public User(String username, String password, String name, String bio, String website, AccountVisType accountVis) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.name = name;
		this.bio = bio;
		this.website = website;
		this.profileImg = profileImg;
		this.accountVis = accountVis;
	}
}
