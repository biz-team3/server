package com.bizteam3.server.notification.service;

import com.bizteam3.server.notification.dto.PendingFollowRequestListResponse;

public interface FollowRequestNotificationService {

    PendingFollowRequestListResponse findPendingRequests(Integer receiverUserId);

    void accept(Integer receiverUserId, Integer requestId);

    void reject(Integer receiverUserId, Integer requestId);
}
