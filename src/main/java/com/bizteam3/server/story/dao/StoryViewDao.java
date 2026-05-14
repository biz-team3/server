package com.bizteam3.server.story.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StoryViewDao {
	void insert(
		@Param("userId") Integer userId,
		@Param("storyId") Integer storyId
	);
}
