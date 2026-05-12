package com.bizteam3.server.notification.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Integer notificationId;
    private Integer receiverUserId;
    private Integer actorUserId;
    private NotificationType notificationType;
    private String targetType;
    private Integer targetId;
    private String message;
    private String imageUrl;
    private Integer isRead;
    private LocalDateTime createdAt;
}
