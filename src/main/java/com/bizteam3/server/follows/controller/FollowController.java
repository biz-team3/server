package com.bizteam3.server.follows.controller;

import java.util.List;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.service.FollowService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 팔로우 관계 생성/삭제, 팔로워/팔로잉 목록 조회,
 * 비공개 계정 팔로우 요청 수락/거절 API
 *
 * TODO: 인증 기능 연동 후 loginUserId 를 SecurityContext 에서 가져오도록 교체 필요
 */
@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // ----------------------------------------------------------------
    // 팔로우 / 언팔로우
    // ----------------------------------------------------------------

    /**
     * POST /api/follows/{targetUserId}
     * - 공개 계정: 즉시 팔로우 관계 생성 → 204
     * - 비공개 계정: 팔로우 요청 생성(PENDING) → 204
     */
    @PostMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(@PathVariable("targetUserId") Integer targetUserId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 1;
        followService.follow(loginUserId, targetUserId);
    }

    /**
     * DELETE /api/follows/{targetUserId}
     * 팔로우 관계 삭제 (언팔로우) → 204
     */
    @DeleteMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@PathVariable("targetUserId") Integer targetUserId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 1;
        followService.unfollow(loginUserId, targetUserId);
    }

    // ----------------------------------------------------------------
    // 팔로워 / 팔로잉 목록 조회
    // ----------------------------------------------------------------

    /** GET /api/follows/{userId}/followers?page=0&size=20 - 팔로워 목록 */
    @GetMapping("/{userId}/followers")
    public PageResponse<FollowUserResponse> getFollowers(
        @PathVariable("userId") Integer userId,
        @Valid PageRequest pageRequest
    ) {
        return followService.findFollowers(userId, pageRequest);
    }

    /** GET /api/follows/{userId}/following?page=0&size=20 - 팔로잉 목록 */
    @GetMapping("/{userId}/following")
    public PageResponse<FollowUserResponse> getFollowing(
        @PathVariable("userId") Integer userId,
        @Valid PageRequest pageRequest
    ) {
        return followService.findFollowing(userId, pageRequest);
    }

    // ----------------------------------------------------------------
    // 팔로우 요청 관리 (비공개 계정 전용)
    // ----------------------------------------------------------------

    /**
     * GET /api/follows/requests
     * 나에게 온 PENDING 팔로우 요청 목록 조회
     * (비공개 계정 본인만 의미 있는 데이터를 볼 수 있음)
     */
    @GetMapping("/requests")
    public List<FollowRequestResponse> getPendingRequests() {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 1;
        return followService.findPendingRequests(loginUserId);
    }

    /**
     * POST /api/follows/requests/{requestId}/accept
     * 팔로우 요청 수락 → ACCEPTED + follows 관계 생성 → 204
     */
    @PostMapping("/requests/{requestId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptRequest(@PathVariable("requestId") Integer requestId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 1;
        followService.acceptRequest(loginUserId, requestId);
    }

    /**
     * POST /api/follows/requests/{requestId}/reject
     * 팔로우 요청 거절 → REJECTED → 204
     */
    @PostMapping("/requests/{requestId}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectRequest(@PathVariable("requestId") Integer requestId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 1;
        followService.rejectRequest(loginUserId, requestId);
    }
}
