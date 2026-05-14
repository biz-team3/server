package com.bizteam3.server.notification.service;

import com.bizteam3.server.notification.entity.NotificationType;

public interface NotificationInvalidationService {

    void deleteEvent(
        Integer receiverUserId,
        Integer actorUserId,
        NotificationType notificationType,
        String targetType,
        Integer targetId
    );

    void deleteSource(String sourceType, Integer sourceId);

    void deleteTarget(String targetType, Integer targetId);
}
