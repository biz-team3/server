package com.bizteam3.server.notification.controller;

import com.bizteam3.server.notification.dto.PendingFollowRequestListResponse;
import com.bizteam3.server.notification.service.FollowRequestNotificationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 알림 패널에서 사용하는 팔로우 요청 API
 *
 * TODO: 인증 기능 연동 후 loginUserId 를 SecurityContext 에서 가져오도록 교체 필요
 */
@RestController
@RequestMapping("/api/follow-requests")
@RequiredArgsConstructor
public class FollowRequestNotificationController {

    private final FollowRequestNotificationService followRequestNotificationService;

    /** GET /api/follow-requests - 받은 PENDING 팔로우 요청 목록 */
    @GetMapping
    public PendingFollowRequestListResponse getFollowRequests() {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 2;
        return followRequestNotificationService.findPendingRequests(loginUserId);
    }

    /** POST /api/follow-requests/{requestId}/accept - 팔로우 요청 수락 */
    @PostMapping("/{requestId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFollowRequest(@PathVariable Integer requestId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 2;
        followRequestNotificationService.accept(loginUserId, requestId);
    }

    /** DELETE /api/follow-requests/{requestId} - 팔로우 요청 거절 */
    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectFollowRequest(@PathVariable Integer requestId) {
        // TODO: 인증 기능 연결 후 로그인 사용자 ID로 교체 필요
        Integer loginUserId = 2;
        followRequestNotificationService.reject(loginUserId, requestId);
    }
}
