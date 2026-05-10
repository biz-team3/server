package com.bizteam3.server.post.service;

import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.NotFoundException;
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

    @Transactional
    public void likePost(Integer userId, Integer postId) {
        Like like = new Like(
                userId,
                postId
        );

        if (postDao.countByPostId(postId) == 0) {
            throw new NotFoundException(ErrorCode.NOT_FOUND, "게시물을 찾을 수 없습니다.");
        }

        if (likeDao.isLiked(userId, postId) > 0) {
            return;
        }

        likeDao.insert(like);
    }
}
