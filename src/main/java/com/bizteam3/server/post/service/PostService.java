package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dto.PostCreateRequest;

public interface PostService {
    void createPost(PostCreateRequest request, Integer userId);
    boolean deletePost(Integer postId);
}
