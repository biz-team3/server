package com.bizteam3.server.story.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Story {
	Integer storyId;
	Integer userId;
	String imageUrl;
	LocalDateTime createdAt;
	LocalDateTime expiresAt;
	LocalDateTime deletedAt;

	public Story(Integer userId, String imageUrl) {
		this.userId = userId;
		this.imageUrl = imageUrl;
	}
}
