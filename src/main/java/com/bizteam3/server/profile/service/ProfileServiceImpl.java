package com.bizteam3.server.profile.service;

import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final UserDao userDao;
    private final FollowDao followDao;
    private final PostDao postDao;

    @Transactional
    ProfileResponse myProfile(Integer userId){
        User user = userDao.selectById(userId);

        if(user == null || user.getDeleteAt() == null){
            throw NotFoundException.of("User", userId);
        }
        int followerCount = followDao.countFollowers(userId);
        int followingCount = followDao.countFollowing(userId);
        int postCount = postDao.countByUserId(userId);

        return ProfileResponse.fromMe(
                user,
                followerCount,
                followingCount,
                postCount
        );
    }
}
