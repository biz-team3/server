package com.bizteam3.server.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationSummaryResponse {
    private final int unreadNotificationCount;
    private final int pendingFollowRequestCount;
    private final int totalBadgeCount;
}
