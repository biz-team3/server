package com.bizteam3.server.follows.dao;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.entity.Follow;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * follows 테이블을 조회/변경하는 MyBatis DAO입니다.
 *
 * TODO: SQL은 기존 UserMapper/PostDao.xml 방식에 맞춰 XML 매퍼에서 작성합니다.
 */
@Mapper
public interface FollowDao {

	int insert(Follow follow);

	int deleteByUsers(
		@Param("followerUserId") Integer followerUserId,
		@Param("followingUserId") Integer followingUserId
	);

	int countByUsers(
		@Param("followerUserId") Integer followerUserId,
		@Param("followingUserId") Integer followingUserId
	);

	int countActiveUser(@Param("userId") Integer userId);

	List<FollowUserResponse> selectFollowers(@Param("userId") Integer userId);

	List<FollowUserResponse> selectFollowing(@Param("userId") Integer userId);
}
