package com.bizteam3.server.post.service;

import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.notification.dao.NotificationDao;
import com.bizteam3.server.notification.entity.Notification;
import com.bizteam3.server.notification.entity.NotificationType;
import com.bizteam3.server.notification.service.NotificationInvalidationService;
import com.bizteam3.server.post.dao.LikeDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeDao likeDao;
    private final PostDao postDao;
    private final NotificationDao notificationDao;
    private final NotificationInvalidationService notificationInvalidationService;

    @Transactional
    public void likePost(Integer userId, Integer postId) {
        if (postDao.countByPostId(postId) == 0) {
            throw new NotFoundException(ErrorCode.NOT_FOUND, "게시물을 찾을 수 없습니다.");
        }

        Like like = new Like(
                userId,
                postId
        );

        if (likeDao.isLiked(userId, postId) > 0) {
            return;
        }

        likeDao.insert(like);

        Integer postOwnerId = postDao.selectUserId(postId);
        if (!userId.equals(postOwnerId)) {
            if (notificationDao.countByEvent(postOwnerId, userId, NotificationType.LIKE, "POST", postId) > 0) {
                return;
            }

            notificationDao.insert(new Notification(
                    postOwnerId,
                    userId,
                    NotificationType.LIKE,
                    "POST",
                    postId,
                    "게시물에 좋아요를 눌렀습니다."
            ));
        }
    }

    @Transactional
    public void unlikePost(Integer userId, Integer postId) {
        if (postDao.countByPostId(postId) == 0) {
            throw new NotFoundException(ErrorCode.NOT_FOUND, "게시물을 찾을 수 없습니다.");
        }

        Like like = new Like(
                userId,
                postId
        );

        if (likeDao.isLiked(userId, postId) == 0) {
            return;
        }

        likeDao.delete(like);

        Integer postOwnerId = postDao.selectUserId(postId);
        if (!userId.equals(postOwnerId)) {
            notificationInvalidationService.deleteEvent(postOwnerId, userId, NotificationType.LIKE, "POST", postId);
        }
    }
}
