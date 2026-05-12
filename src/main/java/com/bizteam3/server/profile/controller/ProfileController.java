package com.bizteam3.server.profile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.profile.dto.ContentResponse;
import com.bizteam3.server.profile.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
	private final ProfileService service;

	public ProfileController(ProfileService service) {
		this.service = service;
	}

	@GetMapping("/users/{userId}/posts")
	public PageResponse<ContentResponse> getProfilePosts(
		@PathVariable Integer userId,
		@Valid PageRequest pageRequest
	){
		return service.getPosts(userId, pageRequest);
	}
}
