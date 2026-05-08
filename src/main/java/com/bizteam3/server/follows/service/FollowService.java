package com.bizteam3.server.follows.service;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.dto.FollowUserResponse;

public interface FollowService {

    /**
     * 팔로우 요청
     * - 공개 계정: follows 테이블에 즉시 관계 생성
     * - 비공개 계정: follow_requests 테이블에 PENDING 요청 생성
     */
    void follow(Integer followerUserId, Integer targetUserId);

    /** 팔로우 관계 삭제 (언팔로우) */
    void unfollow(Integer followerUserId, Integer targetUserId);

    /** targetUserId 의 팔로워 목록 */
    List<FollowUserResponse> findFollowers(Integer userId);

    /** targetUserId 의 팔로잉 목록 */
    List<FollowUserResponse> findFollowing(Integer userId);

    /**
     * 나(receiverUserId)에게 온 PENDING 팔로우 요청 목록 조회
     * 비공개 계정 본인만 호출 가능 (Controller에서 loginUserId == receiverUserId 보장)
     */
    List<FollowRequestResponse> findPendingRequests(Integer receiverUserId);

    /**
     * 팔로우 요청 수락
     * - follow_requests 상태 → ACCEPTED
     * - follows 테이블에 실제 관계 생성
     *
     * @param receiverUserId 수신자(로그인 사용자) ID - 소유권 검증에 사용
     * @param requestId      수락할 요청 PK
     */
    void acceptRequest(Integer receiverUserId, Integer requestId);

    /**
     * 팔로우 요청 거절
     * - follow_requests 상태 → REJECTED
     *
     * @param receiverUserId 수신자(로그인 사용자) ID - 소유권 검증에 사용
     * @param requestId      거절할 요청 PK
     */
    void rejectRequest(Integer receiverUserId, Integer requestId);
}
