package com.bizteam3.server.story.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryResponse {
	private final Integer userId;
	private final String username;
	private final String profileImageUrl;
	boolean isOwner;
	List<StoryResponse> stories;

	public static UserStoryResponse toDto(
		Integer userId,
		String username,
		String profileImageUrl,
		boolean isOwner,
		List<StoryResponse> stories
	){
		return UserStoryResponse.builder()
			.userId(userId)
			.username(username)
			.profileImageUrl(profileImageUrl)
			.isOwner(isOwner)
			.stories(stories)
			.build();
	}
}
