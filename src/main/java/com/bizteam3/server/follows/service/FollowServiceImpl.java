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
 * follows 테이블 기준의 기본 팔로우 관계를 처리
 */
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final FollowDao followDao;

	/**
	 * 공개 계정 팔로우처럼 즉시 관계가 생기는 기본 흐름
	 */
	@Transactional
	public void follow(Integer followerUserId, Integer targetUserId) {
		validateDifferentUser(followerUserId, targetUserId);
		validateActiveUser(targetUserId);

		// 이미 팔로우 중이면 중복 생성 없이 성공 처리
		// TODO: 팀 의사결정 필요 - 중복 팔로우 요청을 409 Conflict로 명확히 막을지,
		//       멱등성(idempotency) 원칙에 따라 이미 원하는 상태이므로 성공 처리할지 결정 필요
		// 멱등성 정리: 같은 요청이 여러 번 들어와도 최종 서버 상태가 같게 유지되는 성질임
		// 필요한 이유: 사용자가 버튼을 빠르게 두 번 누르거나 네트워크 재시도로 같은 요청이 다시 들어올 수 있음
		// 현재 docs 기준: "중복 요청/중복 팔로우는 멱등 처리"라고 되어 있어 성공 처리 방향으로 작성함
		// TODO: 팀원들과 공유/스터디 후 최종 정책이 정해지면 이 설명 주석 정리 필요
		if (followDao.countByUsers(followerUserId, targetUserId) > 0) {
			return;
		}

		int rows = followDao.insert(new Follow(followerUserId, targetUserId));
		if (rows != 1) {
			throw new DatabaseException("팔로우 처리에 실패하였습니다.");
		}
	}

	/**
	 * 팔로우 관계를 삭제
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

	/**
	 * 로그인 사용자와 팔로우 대상 사용자가 서로 다른지 검증
	 *
	 * @param loginUserId 현재 로그인한 사용자 ID
	 * @param targetUserId 팔로우 대상 사용자 ID
	 * @throws BadRequestException 자기 자신을 팔로우하려는 경우
	 */
	private void validateDifferentUser(Integer loginUserId, Integer targetUserId) {
		// 방어적 프로그래밍: 프론트에서 내 프로필에 팔로우 버튼을 숨겨도 서버 검증은 별도로 필요함
		// 사용자가 브라우저 콘솔, Postman, curl 등으로 직접 자기 자신 팔로우 요청을 보낼 수 있음
		// UI에서 안 보임과 서버에서 불가능함은 다르므로 데이터 정합성을 지키기 위한 최소 방어선임
		if (loginUserId.equals(targetUserId)) {
			throw new BadRequestException("자기 자신은 팔로우할 수 없습니다.");
		}
	}

	/**
	 * 삭제되지 않은 활성 사용자인지 검증
	 *
	 * @param userId 검증할 사용자 ID
	 * @throws NotFoundException 사용자가 없거나 탈퇴 처리된 경우
	 */
	private void validateActiveUser(Integer userId) {
		if (followDao.countActiveUser(userId) != 1) {
			throw NotFoundException.of("User", userId);
		}
	}
}
