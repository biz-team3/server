package com.bizteam3.server.notification.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.notification.dto.MarkNotificationsReadRequest;
import com.bizteam3.server.notification.dto.NotificationListResponse;
import com.bizteam3.server.notification.dto.NotificationSummaryResponse;
import com.bizteam3.server.notification.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반 알림 패널/배지 API
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /** GET /api/notifications - 현재 사용자의 알림 목록 */
    @GetMapping
    @AccessTokenCheck
    public NotificationListResponse getNotifications(HttpServletRequest request) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        return notificationService.findNotifications(loginUserId);
    }

    /** GET /api/notifications/summary - 알림 배지 수 요약 */
    @GetMapping("/summary")
    @AccessTokenCheck
    public NotificationSummaryResponse getSummary(HttpServletRequest request) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        return notificationService.getSummary(loginUserId);
    }

    /** PATCH /api/notifications/read - 전체 또는 선택 알림 읽음 처리 */
    @PatchMapping("/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void markRead(
        @RequestBody(required = false) MarkNotificationsReadRequest requestBody,
        HttpServletRequest request
    ) {
        Integer loginUserId = (Integer) request.getAttribute("userId");
        notificationService.markRead(
            loginUserId,
            requestBody == null ? null : requestBody.getNotificationIds()
        );
    }
}
