package com.bizteam3.server.post.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	Integer postId;
	Integer userId;
	String caption;
	String translatedCaption;
	LocalDateTime createdAt;
	LocalDateTime updateAt;
	LocalDateTime deleteAt;

	public Post(Integer userId, String caption, String translatedCaption) {
		this.userId = userId;
		this.caption = caption;
		this.translatedCaption = translatedCaption;
	}
}
