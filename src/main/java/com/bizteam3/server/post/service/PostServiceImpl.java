package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dao.HashtagDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.entity.Hashtag;
import com.bizteam3.server.post.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final HashtagDao hashtagDao;

    public PostServiceImpl(PostDao postDao, HashtagDao hashtagDao) {
        this.postDao = postDao;
        this.hashtagDao = hashtagDao;
    }

    @Override
    @Transactional
    public void CreatePost(PostCreateRequest request, Integer userId) {
        Post post = new Post(
                userId,
                request.getCaption(),
                request.getTranslatedCaption()
        );

        int insertedCheck = postDao.insert(post);

        if (insertedCheck > 0) {
            Set<String> hashtagSet = post.createHashtag();

            if (!hashtagSet.isEmpty()) {
                hashtagDao.insertHashtag(hashtagSet);
                hashtagDao.insertPostHashtag(post.getPostId(), hashtagSet);
            }
        }
    }

}
