package com.bizteam3.server.notification.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class NotificationListResponse {
    private final List<NotificationResponse> notifications;

    private NotificationListResponse(List<NotificationResponse> notifications) {
        this.notifications = notifications;
    }

    public static NotificationListResponse of(List<NotificationResponse> notifications) {
        return new NotificationListResponse(notifications);
    }
}
