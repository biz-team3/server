package com.bizteam3.server.story.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bizteam3.server.story.dao.row.StoryRow;
import com.bizteam3.server.story.entity.Story;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryResponse {
	Integer storyId;
	String imageUrl;
	Boolean isRead;
	LocalDateTime createdAt;

	public static List<StoryResponse> toListDto(List<StoryRow> stories) {
		return stories.stream()
			.map(story -> StoryResponse.builder()
				.storyId(story.getStoryId())
				.imageUrl(story.getImageUrl())
				.isRead(story.getIsRead())
				.createdAt(story.getCreatedAt())
				.build())
			.toList();
	}
}
