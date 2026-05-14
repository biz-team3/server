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

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.story.dto.UserStoryResponse;
import com.bizteam3.server.story.service.StoryService;
import com.bizteam3.server.story.service.StoryViewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
	private final StoryService storyService;
	private final StoryViewService viewService;

	public StoryController(StoryService storyService, StoryViewService viewService) {
		this.storyService = storyService;
		this.viewService = viewService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@AccessTokenCheck
	public void createStory(
		@RequestPart("file") MultipartFile file,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		storyService.create(userId, file);
	}

	@GetMapping("/{userId}")
	@AccessTokenCheck
	public UserStoryResponse getUserStory(
		@PathVariable Integer userId,
		HttpServletRequest httpServletRequest
	) {
		Integer myId = (Integer) httpServletRequest.getAttribute("userId");
		return storyService.getFeed(userId, myId);
	}

	@GetMapping("/feed")
	@AccessTokenCheck
	public PageResponse<UserStoryResponse> getGroupStory(
		@Valid PageRequest pageRequest,
		HttpServletRequest httpServletRequest
	) {
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		return storyService.getFeeds(userId, pageRequest);
	}

	@DeleteMapping("/{storyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@AccessTokenCheck
	public void deleteStory(
		@PathVariable Integer storyId,
		HttpServletRequest httpServletRequest
	) {
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		storyService.delete(userId, storyId);
	}

	@PostMapping("/view/{storyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@AccessTokenCheck
	public void seeStory(
		@PathVariable Integer storyId,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		viewService.see(userId, storyId);
	}
}
