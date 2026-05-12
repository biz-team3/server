package com.bizteam3.server.notification.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 알림 패널에서 사용하는 팔로우 요청 API
 */
@RestController
@RequestMapping("/api/follow-requests")
@RequiredArgsConstructor
public class FollowRequestNotificationController {

    private final FollowRequestNotificationService followRequestNotificationService;

    /** GET /api/follow-requests - 받은 PENDING 팔로우 요청 목록 */
    @GetMapping
    @AccessTokenCheck
    public PendingFollowRequestListResponse getFollowRequests(
        HttpServletRequest httpServletRequest
    ) {
        Integer loginUserId = (Integer) httpServletRequest.getAttribute("userId");
        return followRequestNotificationService.findPendingRequests(loginUserId);
    }

    /** POST /api/follow-requests/{requestId}/accept - 팔로우 요청 수락 */
    @PostMapping("/{requestId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void acceptFollowRequest(
        @PathVariable Integer requestId,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followRequestNotificationService.accept(loginUserId, requestId);
    }

    /** DELETE /api/follow-requests/{requestId} - 팔로우 요청 거절 */
    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void rejectFollowRequest(
        @PathVariable Integer requestId,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        followRequestNotificationService.reject(loginUserId, requestId);
    }
}
