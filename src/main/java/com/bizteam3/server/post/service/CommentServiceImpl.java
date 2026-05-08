package com.bizteam3.server.post.service;

import org.springframework.stereotype.Service;

import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.post.dao.CommentDao;
import com.bizteam3.server.post.dto.CommentCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentDao dao;

	@Override
	public void create(Integer postId, Integer userId, CommentCreateRequest request) {
		int insert = dao.insert(CommentCreateRequest.toEntity(postId, userId, request));
		if(insert != 1)
			throw new DatabaseException("저장에 실패했습니다");
	}
}
