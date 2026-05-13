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

    /** 알림을 발생시킨 사용자 id. 예: 나를 팔로우하거나 좋아요를 누른 사용자 */
    private Integer actorUserId;

    private NotificationType notificationType;
    private String targetType;
    private Integer targetId;
    private String message;
    private String imageUrl;
    private Integer isRead;
    private LocalDateTime createdAt;
}
