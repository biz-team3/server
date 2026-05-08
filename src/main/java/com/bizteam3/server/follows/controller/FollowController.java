package com.bizteam3.server.follows.controller;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.service.FollowService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 팔로우 관계 생성/삭제와 팔로워/팔로잉 목록 조회 API입니다.
 */
@RestController
@RequestMapping("/api/follows")
public class FollowController {
	private final FollowService followService;

	public FollowController(FollowService followService) {
		this.followService = followService;
	}

	@PostMapping("/{targetUserId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void follow(@PathVariable("targetUserId") Integer targetUserId) {
		// TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
		Integer loginUserId = 1;
		followService.follow(loginUserId, targetUserId);
	}

	@DeleteMapping("/{targetUserId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unfollow(@PathVariable("targetUserId") Integer targetUserId) {
		// TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
		Integer loginUserId = 1;
		followService.unfollow(loginUserId, targetUserId);
	}

	@GetMapping("/{userId}/followers")
	public List<FollowUserResponse> getFollowers(@PathVariable("userId") Integer userId) {
		return followService.findFollowers(userId);
	}

	@GetMapping("/{userId}/following")
	public List<FollowUserResponse> getFollowing(@PathVariable("userId") Integer userId) {
		return followService.findFollowing(userId);
	}
}
