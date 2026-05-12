package com.bizteam3.server.follows.controller;

import java.util.List;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.service.FollowService;
import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    /**
     * POST /api/follows/{targetUserId}
     */
    @PostMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void follow(
        @PathVariable Integer targetUserId,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followService.follow(loginUserId, targetUserId);
    }

    /**
     * DELETE /api/follows/{targetUserId}
     */
    @DeleteMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void unfollow(
        @PathVariable Integer targetUserId,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followService.unfollow(loginUserId, targetUserId);
    }

    /** GET /api/follows/{userId}/followers?page=0&size=20 - 팔로워 목록 & 페이지네이션 */
    @GetMapping("/{userId}/followers")
    @AccessTokenCheck
    public PageResponse<FollowUserResponse> getFollowers(
		@PathVariable Integer userId,
        @Valid PageRequest pageRequest,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        return followService.findFollowers(loginUserId, userId, pageRequest);
    }

    /** GET /api/follows/{userId}/following?page=0&size=20 - 팔로잉 목록 & 페이지네이션 */
    @GetMapping("/{userId}/following")
    @AccessTokenCheck
    public PageResponse<FollowUserResponse> getFollowing(
		@PathVariable Integer userId,
        @Valid PageRequest pageRequest,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        return followService.findFollowing(loginUserId, userId, pageRequest);
    }

    /**
     * GET /api/follows/requests
     * 나에게 온 PENDING 팔로우 요청 목록 조회
     * (비공개 계정 본인만 의미 있는 데이터를 볼 수 있음)
     */
    @GetMapping("/requests")
    @AccessTokenCheck
    public List<FollowRequestResponse> getPendingRequests(
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        return followService.findPendingRequests(loginUserId);
    }

    /**
     * POST /api/follows/requests/{requestId}/accept
     */
    @PostMapping("/requests/{requestId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void acceptRequest(
        @PathVariable Integer requestId,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followService.acceptRequest(loginUserId, requestId);
    }

    /**
     * POST /api/follows/requests/{requestId}/reject
     */
    @PostMapping("/requests/{requestId}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void rejectRequest(
        @PathVariable Integer requestId,
        HttpServletRequest request) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followService.rejectRequest(loginUserId, requestId);
    }
}
