package com.bizteam3.server.profile.controller;

import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.profile.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;

    @GetMapping("/me")
    public ProfileResponse myProfile(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        return profileService.myProfile(userId);
    }

	@GetMapping("/{userId}")
	public ProfileResponse getProfilesByUserId(
			@PathVariable("userId") Integer userId,
			HttpServletRequest request){
		Integer viewerId = (Integer) request.getAttribute("userId");
		return profileService.getProfileByUserId(userId, viewerId);
	}

	@GetMapping("/by-username/{username}")
	public ProfileResponse getProfilesByUsername(
			@PathVariable String username,
			HttpServletRequest request){
		Integer viewerId = (Integer) request.getAttribute("userId");
		return profileService.getProfileByUsername(username, viewerId);
	}

	@GetMapping("/users/{userId}/posts")
	public PageResponse<ContentResponse> getProfilePosts(
		@PathVariable Integer userId,
		@Valid PageRequest pageRequest
	){
		return profileService.getPosts(userId, pageRequest);
	}
}
