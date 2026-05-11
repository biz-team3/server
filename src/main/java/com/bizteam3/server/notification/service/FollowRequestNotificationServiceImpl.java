package com.bizteam3.server.notification.service;

import java.util.List;

import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dao.FollowRequestDao;
import com.bizteam3.server.follows.service.FollowService;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.notification.dto.PendingFollowRequestListResponse;
import com.bizteam3.server.notification.dto.PendingFollowRequestResponse;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowRequestNotificationServiceImpl implements FollowRequestNotificationService {

    private final FollowRequestDao followRequestDao;
    private final FollowDao        followDao;
    private final FollowService    followService;

    @Override
    public PendingFollowRequestListResponse findPendingRequests(Integer receiverUserId) {
        validateActiveUser(receiverUserId);

        List<PendingFollowRequestResponse> requests =
            followRequestDao.selectPendingNotificationByReceiver(receiverUserId);

        return PendingFollowRequestListResponse.of(requests);
    }

    @Override
    public void accept(Integer receiverUserId, Integer requestId) {
        followService.acceptRequest(receiverUserId, requestId);
    }

    @Override
    public void reject(Integer receiverUserId, Integer requestId) {
        followService.rejectRequest(receiverUserId, requestId);
    }

    private void validateActiveUser(Integer userId) {
        if (followDao.countActiveUser(userId) != 1) {
            throw NotFoundException.of("User", userId);
        }
    }
}
