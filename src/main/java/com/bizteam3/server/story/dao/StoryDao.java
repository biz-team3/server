package com.bizteam3.server.story.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bizteam3.server.story.dao.row.StoryRow;
import com.bizteam3.server.story.entity.Story;
import com.bizteam3.server.user.entity.User;

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

	List<StoryRow> selectStoriesByUserId(
		@Param("userId") Integer userId,
		@Param("viewerId") Integer viewerId
	);


	List<User> selectFeedStoryUsersByViewerId(
		@Param("viewerId") Integer viewerId,
		@Param("offset") int offset,
		@Param("size") int size
		);

	int countFeedStoryUsersByViewerId(
		@Param("userId") Integer userId
	);
}
