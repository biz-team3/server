package com.bizteam3.server.save.service;

import com.bizteam3.server.global.exception.common.ConflictException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.save.dao.SaveDao;
import com.bizteam3.server.save.entity.Save;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveServiceImpl implements SaveService{
    private final SaveDao saveDao;
    private final PostDao postDao;

    @Transactional
    public void save(Integer postId, Integer userId) {
        if (!postDao.existsByPostId(postId)) { //postId 반환
            throw new NotFoundException("게시물을 찾을 수 없습니다.");
        }

        if (saveDao.existsByUserIdAndPostId(userId, postId)) {
            throw new ConflictException("이미 저장한 게시물입니다.");
        }

        Save save = new Save(userId, postId);
        int rows = saveDao.insert(save);

        if (rows != 1) {
            throw new DatabaseException("게시물 저장에 실패했습니다.");
        }
    }

    @Transactional
    public void unsave(Integer postId, Integer userId) {
        //TODO: 탈퇴시 유저의 post가 사라진걸 고려하여, 자동삭제를 추가할지
        if (!postDao.existsByPostId(postId)) {
            throw new NotFoundException("게시물을 찾을 수 없습니다.");
        }

        int rows = saveDao.deleteByUserIdAndPostId(userId, postId);

        if (rows != 1) {
            throw new NotFoundException("저장 내역을 찾을 수 없습니다.");
        }
    }
}
