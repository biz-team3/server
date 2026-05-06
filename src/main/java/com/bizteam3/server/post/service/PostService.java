package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dto.CreatePostRequest;
import com.bizteam3.server.post.dto.CreatePostResponse;

public interface PostService {
	CreatePostResponse createPost(CreatePostRequest request, Integer userId);
}
