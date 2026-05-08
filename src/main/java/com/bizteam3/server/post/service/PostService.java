package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.dto.PostUpdateCaptionRequest;

public interface PostService {
    void createPost(PostCreateRequest request, Integer userId);

    void updateCaption(Integer postId, PostUpdateCaptionRequest request, Integer userId);
}
