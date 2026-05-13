package com.bizteam3.server.notification.service;

import java.util.List;

import com.bizteam3.server.notification.dto.NotificationListResponse;
import com.bizteam3.server.notification.dto.NotificationSummaryResponse;

public interface NotificationService {

    NotificationListResponse findNotifications(Integer receiverUserId);

    NotificationSummaryResponse getSummary(Integer receiverUserId);

    void markRead(Integer receiverUserId, List<Integer> notificationIds);
}
