package com.bizteam3.server.story.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bizteam3.server.story.entity.Story;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryResponse {
	Integer storyId;
	String imageUrl;
	LocalDateTime createdAt;

	public static List<StoryResponse> toListDto(List<Story> stories) {
		return stories.stream()
			.map(story -> StoryResponse.builder()
				.storyId(story.getStoryId())
				.imageUrl(story.getImageUrl())
				.createdAt(story.getCreatedAt())
				.build())
			.toList();
	}
}
