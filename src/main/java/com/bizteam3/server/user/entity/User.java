package com.bizteam3.server.user.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	Integer userId;
	String password;
	String name;
	String bio;
	String website;
	String profileImg;
	AccountVisType accountVis;
	LocalDateTime createdAt;
	LocalDateTime updateAt;
	LocalDateTime deleteAt;

	public User(Integer userId, String password, String name, String bio, String website, String profileImg,
		AccountVisType accountVis, LocalDateTime createdAt, LocalDateTime updateAt, LocalDateTime deleteAt) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.bio = bio;
		this.website = website;
		this.profileImg = profileImg;
		this.accountVis = accountVis;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.deleteAt = deleteAt;
	}


}
