package com.bizteam3.server.story.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.story.dto.StoryGroupResponse;
import com.bizteam3.server.story.dto.UserStoryResponse;
import com.bizteam3.server.story.service.StoryService;


@RestController
@RequestMapping("/api/stories")
public class StoryController {
	private final StoryService service;

	public StoryController(StoryService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createStory(
		@RequestPart("file") MultipartFile file
	){
		//TODO: JWT
		Integer userId = 1;
		service.create(userId, file);
	}

	@GetMapping("/{userId}")
	public UserStoryResponse getUserStory(
		@PathVariable Integer userId
	) {
		return service.getFeed(userId);
	}

	@GetMapping("/feed")
	public StoryGroupResponse getGroupStory() {
		//TODO: JWT
		Integer userId = 1;
		return service.getFeeds(userId);
	}

	@DeleteMapping("/{storyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStory(
		@PathVariable Integer storyId
	) {
		//TODO: JWT
		Integer userId = 1;
		service.delete(userId, storyId);
	}
}
