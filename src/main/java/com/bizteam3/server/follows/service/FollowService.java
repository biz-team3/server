package com.bizteam3.server.follows.service;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowUserResponse;

public interface FollowService {
	void follow(Integer followerUserId, Integer targetUserId);

	void unfollow(Integer followerUserId, Integer targetUserId);

	List<FollowUserResponse> findFollowers(Integer userId);

	List<FollowUserResponse> findFollowing(Integer userId);
}
