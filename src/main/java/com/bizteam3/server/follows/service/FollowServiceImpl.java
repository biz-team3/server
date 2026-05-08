package com.bizteam3.server.follows.service;

import java.util.List;

import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.entity.Follow;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * follows 테이블 기준의 기본 팔로우 관계를 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final FollowDao followDao;

	/**
	 * 공개 계정 팔로우처럼 즉시 관계가 생기는 기본 흐름입니다.
	 */
	@Transactional
	public void follow(Integer followerUserId, Integer targetUserId) {
		validateDifferentUser(followerUserId, targetUserId);
		validateActiveUser(targetUserId);

		// 이미 팔로우 중이면 중복 생성 없이 성공 처리합니다.
		if (followDao.countByUsers(followerUserId, targetUserId) > 0) {
			return;
		}

		int rows = followDao.insert(new Follow(followerUserId, targetUserId));
		if (rows != 1) {
			throw new DatabaseException("팔로우 처리에 실패하였습니다.");
		}
	}

	/**
	 * 팔로우 관계를 삭제합니다. 없는 관계 삭제는 멱등하게 성공 처리합니다.
	 */
	@Transactional
	public void unfollow(Integer followerUserId, Integer targetUserId) {
		validateDifferentUser(followerUserId, targetUserId);
		validateActiveUser(targetUserId);

		followDao.deleteByUsers(followerUserId, targetUserId);
	}

	public List<FollowUserResponse> findFollowers(Integer userId) {
		validateActiveUser(userId);
		return followDao.selectFollowers(userId);
	}

	public List<FollowUserResponse> findFollowing(Integer userId) {
		validateActiveUser(userId);
		return followDao.selectFollowing(userId);
	}

	private void validateDifferentUser(Integer loginUserId, Integer targetUserId) {
		if (loginUserId.equals(targetUserId)) {
			throw new BadRequestException("자기 자신은 팔로우할 수 없습니다.");
		}
	}

	private void validateActiveUser(Integer userId) {
		if (followDao.countActiveUser(userId) != 1) {
			throw NotFoundException.of("User", userId);
		}
	}
}
