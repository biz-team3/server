package com.bizteam3.server.story.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoryGroupResponse {
	List<UserStoryResponse> storyGroups;

	public static StoryGroupResponse toDto(List<UserStoryResponse> userStoryResponses) {
		return new StoryGroupResponse(userStoryResponses);
	}
}
