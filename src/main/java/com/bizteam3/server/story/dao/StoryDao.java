package com.bizteam3.server.story.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bizteam3.server.story.entity.Story;

@Mapper
public interface StoryDao {
	int insert(Story story);

	Story selectByUserIdAndStoryId(
		@Param("userId") Integer userId,
		@Param("storyId") Integer storyId
	);

	int delete(
		@Param("userId") Integer userId,
		@Param("storyId") Integer storyId
	);

	List<Story> selectByUserId(
		@Param("userId") Integer userId
	);
}
