package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.entity.Post;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public void CreatePost(PostCreateRequest request, Integer userId) {
        Post post = new Post(
                userId, //getUserId(userId)
                request.getCaption(),
                request.getTranslatedCaption()
                //hashtag, media 보류
        );

        postDao.insert(post);
    }
}