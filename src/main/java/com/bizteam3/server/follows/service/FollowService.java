package com.bizteam3.server.follows.service;

import java.util.List;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.dto.FollowUserResponse;

public interface FollowService {

    void follow(Integer followerUserId, Integer targetUserId);

    void unfollow(Integer followerUserId, Integer targetUserId);

    /**
     * targetUserId 의 팔로워 목록
     * PageRequest 로 offset/size 계산 후 PageResponse 로 hasNext 응답
     */
    PageResponse<FollowUserResponse> findFollowers(Integer viewerUserId, Integer userId, PageRequest pageRequest);

    /**
     * targetUserId 의 팔로잉 목록
     * User 목록 페이징 구조와 동일하게 공통 DTO 재사용
     */
    PageResponse<FollowUserResponse> findFollowing(Integer viewerUserId, Integer userId, PageRequest pageRequest);

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
