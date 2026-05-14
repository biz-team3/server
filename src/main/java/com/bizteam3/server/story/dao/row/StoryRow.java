package com.bizteam3.server.story.dao.row;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoryRow {
	private final Integer storyId;
	private final Integer userId;
	private final String imageUrl;
	private final Boolean isRead;
	private final LocalDateTime createdAt;
	private final LocalDateTime expiresAt;
	private final LocalDateTime deletedAt;
}
