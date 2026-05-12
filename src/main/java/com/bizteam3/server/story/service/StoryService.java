package com.bizteam3.server.story.service;

import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.story.dto.StoryGroupResponse;
import com.bizteam3.server.story.dto.UserStoryResponse;

public interface StoryService {
	void create(Integer userId, MultipartFile file);

	void delete(Integer userId, Integer storyId);

	UserStoryResponse getFeed(Integer userId);

	StoryGroupResponse getFeeds(Integer userId);
}
