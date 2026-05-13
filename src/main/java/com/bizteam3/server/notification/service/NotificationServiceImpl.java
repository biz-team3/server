package com.bizteam3.server.notification.service;

import java.util.List;

import com.bizteam3.server.follows.dao.FollowRequestDao;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.notification.dao.NotificationDao;
import com.bizteam3.server.notification.dto.NotificationListResponse;
import com.bizteam3.server.notification.dto.NotificationResponse;
import com.bizteam3.server.notification.dto.NotificationSummaryResponse;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;
    private final FollowRequestDao followRequestDao;
    private final UserDao userDao;

    @Override
    public NotificationListResponse findNotifications(Integer receiverUserId) {
        validateActiveUser(receiverUserId);
        List<NotificationResponse> notifications = notificationDao.selectByReceiverUserId(receiverUserId);
        return NotificationListResponse.of(notifications);
    }

    @Override
    public NotificationSummaryResponse getSummary(Integer receiverUserId) {
        validateActiveUser(receiverUserId);

        int unreadNotificationCount = notificationDao.countUnreadByReceiverUserId(receiverUserId);
        int pendingFollowRequestCount = followRequestDao.countPendingByReceiver(receiverUserId);

        return new NotificationSummaryResponse(
            unreadNotificationCount,
            pendingFollowRequestCount,
            unreadNotificationCount + pendingFollowRequestCount
        );
    }

    @Transactional
    @Override
    public void markRead(Integer receiverUserId, List<Integer> notificationIds) {
        validateActiveUser(receiverUserId);

        // id 목록이 없으면 전체 읽음, 있으면 전달된 알림만 읽음 처리함
        if (notificationIds == null || notificationIds.isEmpty()) {
            notificationDao.markAllReadByReceiverUserId(receiverUserId);
            return;
        }

        notificationDao.markSelectedReadByReceiverUserId(receiverUserId, notificationIds);
    }

    private void validateActiveUser(Integer userId) {
        User user = userDao.selectById(userId);
        if (user == null || user.getDeleteAt() != null) {
            throw NotFoundException.of("User", userId);
        }
    }
}
