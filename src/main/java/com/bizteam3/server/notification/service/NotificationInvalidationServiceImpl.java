package com.bizteam3.server.notification.service;

import org.springframework.stereotype.Service;

import com.bizteam3.server.notification.dao.NotificationDao;
import com.bizteam3.server.notification.entity.NotificationType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationInvalidationServiceImpl implements NotificationInvalidationService {

    private final NotificationDao notificationDao;

    @Override
    public void deleteEvent(
        Integer receiverUserId,
        Integer actorUserId,
        NotificationType notificationType,
        String targetType,
        Integer targetId
    ) {
        notificationDao.deleteByEvent(receiverUserId, actorUserId, notificationType, targetType, targetId);
    }

    @Override
    public void deleteSource(String sourceType, Integer sourceId) {
        notificationDao.deleteBySource(sourceType, sourceId);
    }

    @Override
    public void deleteTarget(String targetType, Integer targetId) {
        notificationDao.deleteByTarget(targetType, targetId);
    }
}
