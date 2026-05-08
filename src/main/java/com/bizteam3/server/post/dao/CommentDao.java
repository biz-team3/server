package com.bizteam3.server.post.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bizteam3.server.post.entity.Comment;

@Mapper
public interface CommentDao {
	int insert(Comment comment);
}
