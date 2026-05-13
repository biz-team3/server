package com.bizteam3.server.profile.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.profile.dto.ProfileRequest;
import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.profile.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.profile.dto.ContentResponse;
import com.bizteam3.server.profile.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;

    @GetMapping("/me")
	@AccessTokenCheck
    public ProfileResponse myProfile(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        return profileService.myProfile(userId);
    }

	@GetMapping("/{userId}")
	@AccessTokenCheck
	public ProfileResponse getProfilesByUserId(
			@PathVariable("userId") Integer userId,
			HttpServletRequest request){
		Integer viewerId = (Integer) request.getAttribute("userId");
		return profileService.getProfileByUserId(userId, viewerId);
	}

	@GetMapping("/by-username/{username}")
	@AccessTokenCheck
	public ProfileResponse getProfilesByUsername(
			@PathVariable String username,
			HttpServletRequest request){
		Integer viewerId = (Integer) request.getAttribute("userId");
		return profileService.getProfileByUsername(username, viewerId);
	}

	@PatchMapping("/users/{userId}")
	@AccessTokenCheck
	public ProfileResponse updateProfile(
			@PathVariable Integer userId,
			@RequestBody ProfileRequest request,
			HttpServletRequest httpRequest
	){
		Integer viewerId = (Integer) httpRequest.getAttribute("userId");
		return profileService.updateProfile(userId, viewerId, request);
	}

	@GetMapping("/users/{userId}/posts")
	@AccessTokenCheck
	public PageResponse<ContentResponse> getProfilePosts(
		@PathVariable Integer userId,
		@Valid PageRequest pageRequest
	){
		return profileService.getPosts(userId, pageRequest);
	}
}
