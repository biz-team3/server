package com.bizteam3.server.post.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Post {
	Integer postId;
	Integer userId;
	String caption;
	String translatedCaption;
	LocalDateTime createdAt;
	LocalDateTime updateAt;
	LocalDateTime deleteAt;

	public Post(Integer postId, Integer userId, String caption, String translatedCaption, LocalDateTime createdAt,
		LocalDateTime updateAt, LocalDateTime deleteAt) {
		this.postId = postId;
		this.userId = userId;
		this.caption = caption;
		this.translatedCaption = translatedCaption;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.deleteAt = deleteAt;
	}
}
