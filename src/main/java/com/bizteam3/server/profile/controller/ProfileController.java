package com.bizteam3.server.profile.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.global.exception.common.InvalidParameterException;
import com.bizteam3.server.profile.dto.ProfileRequest;
import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.profile.dto.vo.ProfileContentType;
import com.bizteam3.server.profile.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.profile.dto.ContentResponse;
import com.bizteam3.server.profile.service.ProfileService;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	private final UserDao userDao;

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
		User byUsername = userDao.findByUsername(request.getUsername());
		if(byUsername != null)
			throw new InvalidParameterException("이미 존재하는 UserName입니다. 다시 입력해주세요.");

		Integer viewerId = (Integer) httpRequest.getAttribute("userId");
		return profileService.updateProfile(userId, viewerId, request);
	}

	@GetMapping("/users/{userId}/posts")
	@AccessTokenCheck
	public PageResponse<ContentResponse> getProfilePosts(
		@PathVariable Integer userId,
		@RequestParam(defaultValue = "POSTS") ProfileContentType type,
		@Valid PageRequest pageRequest,
		HttpServletRequest httpRequest
	){
		Integer viewerId = (Integer) httpRequest.getAttribute("userId");
		return profileService.getPosts(userId, viewerId, type, pageRequest);
	}
}
