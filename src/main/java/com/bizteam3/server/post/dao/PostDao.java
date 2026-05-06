package com.bizteam3.server.post.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bizteam3.server.post.entity.Post;

@Mapper
public interface PostDao {
	int insert(Post post);
}
