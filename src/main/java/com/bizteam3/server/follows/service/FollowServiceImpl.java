package com.bizteam3.server.follows.service;

import java.util.List;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dao.FollowRequestDao;
import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.entity.Follow;
import com.bizteam3.server.follows.entity.FollowRequest;
import com.bizteam3.server.follows.entity.RequestStatus;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.ConflictException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.ForbiddenException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.notification.dao.NotificationDao;
import com.bizteam3.server.notification.entity.Notification;
import com.bizteam3.server.notification.entity.NotificationType;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * follows / follow_requests 테이블을 통합 관리하는 서비스 구현체
 *
 * 팔로우 흐름:
 *  - 공개 계정(PUBLIC)  → follows 테이블에 즉시 관계 삽입
 *  - 비공개 계정(PRIVATE)→ follow_requests 테이블에 PENDING 요청 삽입
 *
 * TODO: 인증 기능 연동 후 loginUserId 를 SecurityContext에서 가져오도록 교체
 */
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowDao        followDao;
    private final FollowRequestDao followRequestDao;
    private final UserDao          userDao;
    private final NotificationDao  notificationDao;

    @Transactional
    @Override
    public void follow(Integer followerUserId, Integer targetUserId) {
        validateDifferentUser(followerUserId, targetUserId);

        User target = getActiveUser(targetUserId);

        if (target.getAccountVis() == AccountVisType.PRIVATE) {
            sendFollowRequest(followerUserId, targetUserId);
        } else {
            followDirectly(followerUserId, targetUserId);
        }
    }

    private void followDirectly(Integer followerUserId, Integer followingUserId) {
        if (followDao.countByUsers(followerUserId, followingUserId) > 0) {
            return; // 이미 팔로우 중 → 멱등 처리
        }
        int rows = followDao.insert(new Follow(followerUserId, followingUserId));
        if (rows != 1) {
            throw new DatabaseException("팔로우 처리에 실패하였습니다.");
        }
    }

    private void sendFollowRequest(Integer requesterUserId, Integer receiverUserId) {
        // 이미 팔로우 중인 경우 요청 불필요
        if (followDao.countByUsers(requesterUserId, receiverUserId) > 0) {
            return;
        }
        // 이미 PENDING 요청이 있으면 중복 요청 불가
        if (followRequestDao.countPending(requesterUserId, receiverUserId) > 0) {
            throw new ConflictException("이미 팔로우 요청을 보냈습니다.");
        }
        int rows = followRequestDao.insert(new FollowRequest(requesterUserId, receiverUserId));
        if (rows != 1) {
            throw new DatabaseException("팔로우 요청 처리에 실패하였습니다.");
        }
    }

    @Transactional
    @Override
    public void unfollow(Integer followerUserId, Integer targetUserId) {
        validateDifferentUser(followerUserId, targetUserId);
        validateActiveUser(targetUserId);
        followDao.deleteByUsers(followerUserId, targetUserId);
    }

    /** 팔로워 목록: UserService.findUsers 와 같은 PageResponse 구조 재사용 */
    @Override
    public PageResponse<FollowUserResponse> findFollowers(Integer viewerUserId, Integer userId, PageRequest pageRequest) {
        validateActiveUser(userId);
        List<FollowUserResponse> followers = followDao.selectFollowers(
            viewerUserId,
            userId,
            pageRequest.getOffset(),
            pageRequest.getSize()
        );
        int total = followDao.countFollowers(userId);
        return new PageResponse<>(followers, pageRequest, total);
    }

    @Override
    public PageResponse<FollowUserResponse> findFollowing(Integer viewerUserId, Integer userId, PageRequest pageRequest) {
        validateActiveUser(userId);
        List<FollowUserResponse> following = followDao.selectFollowing(
            viewerUserId,
            userId,
            pageRequest.getOffset(),
            pageRequest.getSize()
        );
        int total = followDao.countFollowing(userId);
        return new PageResponse<>(following, pageRequest, total);
    }

    @Override
    public List<FollowRequestResponse> findPendingRequests(Integer receiverUserId) {
        validateActiveUser(receiverUserId);
        return followRequestDao.selectPendingByReceiver(receiverUserId);
    }

    @Transactional
    @Override
    public void acceptRequest(Integer receiverUserId, Integer requestId) {
        FollowRequest req = getRequestAndValidateOwner(requestId, receiverUserId);

        int updated = followRequestDao.updateStatus(requestId, RequestStatus.ACCEPTED.name());
        if (updated != 1) {
            throw new DatabaseException("팔로우 요청 수락에 실패하였습니다.");
        }

        // follows 테이블에 실제 관계 생성 (requester → receiver 방향)
        followDirectly(req.getRequesterUserId(), req.getReceiverUserId());
        notificationDao.insert(new Notification(
            req.getReceiverUserId(),
            req.getRequesterUserId(),
            NotificationType.FOLLOW,
            "USER",
            req.getRequesterUserId(),
            "팔로우하기 시작했습니다."
        ));
    }

    @Transactional
    @Override
    public void rejectRequest(Integer receiverUserId, Integer requestId) {
        getRequestAndValidateOwner(requestId, receiverUserId);

        int updated = followRequestDao.updateStatus(requestId, RequestStatus.REJECTED.name());
        if (updated != 1) {
            throw new DatabaseException("팔로우 요청 거절에 실패하였습니다.");
        }
    }
    
    /**
     * 요청 단건 조회 + 수신자 소유권 검증
     * - 요청이 없거나 PENDING 이 아니면 404
     * - receiverUserId 가 요청의 수신자가 아니면 403
     */
    private FollowRequest getRequestAndValidateOwner(Integer requestId, Integer receiverUserId) {
        FollowRequest req = followRequestDao.selectById(requestId);
        if (req == null || req.getStatus() != RequestStatus.PENDING) {
            throw NotFoundException.of("FollowRequest", requestId);
        }
        if (!req.getReceiverUserId().equals(receiverUserId)) {
            throw new ForbiddenException("해당 팔로우 요청에 대한 권한이 없습니다.");
        }
        return req;
    }

    /** 자기 자신 팔로우 방지 */
    private void validateDifferentUser(Integer loginUserId, Integer targetUserId) {
        if (loginUserId.equals(targetUserId)) {
            throw new BadRequestException("자기 자신은 팔로우할 수 없습니다.");
        }
    }

    /** 활성 사용자 존재 여부 검증 */
    private void validateActiveUser(Integer userId) {
        if (followDao.countActiveUser(userId) != 1) {
            throw NotFoundException.of("User", userId);
        }
    }

    /** 활성 사용자 단건 조회 (계정 공개 여부 확인용) */
    private User getActiveUser(Integer userId) {
        User user = userDao.selectById(userId);
        if (user == null || user.getDeleteAt() != null) {
            throw NotFoundException.of("User", userId);
        }
        return user;
    }
}
