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

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.story.dto.StoryGroupResponse;
import com.bizteam3.server.story.dto.UserStoryResponse;
import com.bizteam3.server.story.service.StoryService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
	private final StoryService service;

	public StoryController(StoryService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@AccessTokenCheck
	public void createStory(
		@RequestPart("file") MultipartFile file,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		service.create(userId, file);
	}

	@GetMapping("/{userId}")
	public UserStoryResponse getUserStory(
		@PathVariable Integer userId
	) {
		return service.getFeed(userId);
	}

	@GetMapping("/feed")
	@AccessTokenCheck
	public StoryGroupResponse getGroupStory(
		HttpServletRequest httpServletRequest
	) {
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		return service.getFeeds(userId);
	}

	@DeleteMapping("/{storyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@AccessTokenCheck
	public void deleteStory(
		@PathVariable Integer storyId,
		HttpServletRequest httpServletRequest
	) {
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		service.delete(userId, storyId);
	}
}
