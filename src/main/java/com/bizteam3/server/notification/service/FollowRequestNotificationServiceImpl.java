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

/**
 * 팔로우 요청 알림 패널용 서비스 구현체
 *
 * 현재 단계에서는 팔로우 요청 목록만 follow_requests 테이블을 직접 조회하므로
 * FollowRequestDao, FollowDao, FollowService 를 재사용함.
 *
 * TODO: mutualText 는 공통 팔로워/맞팔 정책이 정해지면 서버 계산값으로 내려주기
 * 현재는 정책 미정이라 null 허용
 *
 * @see com.bizteam3.server.notification.controller.FollowRequestNotificationController
 */
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
