package com.bizteam3.server.story.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoryView {
	Integer viewId;
	Integer userId;
	Integer storyId;
	LocalDateTime createdAt;

}
