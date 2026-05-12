package com.bizteam3.server.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {
    private final Integer notificationId;
    private final String type;
    private final String actorName;
    private final String actorImageUrl;
    private final Integer actorCount;
    private final String targetImageUrl;
    private final Boolean read;
    private final LocalDateTime createdAt;
}
