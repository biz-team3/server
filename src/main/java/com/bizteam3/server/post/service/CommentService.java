package com.bizteam3.server.post.service;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.post.dto.CommentCreateRequest;
import com.bizteam3.server.post.dto.CommentResponse;
import com.bizteam3.server.post.dto.CommentUpdateRequest;

public interface CommentService {


	void create(Integer postId, Integer userId, CommentCreateRequest request);

	void update(Integer commentId, CommentUpdateRequest request);

	void delete(Integer commentId);

	PageResponse<CommentResponse> findComments(Integer postId, Integer userId, PageRequest request);
}
