package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dto.PostCreateRequest;

public interface PostService {
    void CreatePost(PostCreateRequest request, Integer userId);
}
