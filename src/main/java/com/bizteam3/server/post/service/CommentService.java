package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dto.CommentCreateRequest;

public interface CommentService {
	void create(Integer postId, Integer userId, CommentCreateRequest request);
}
