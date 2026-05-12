package com.bizteam3.server.follows.dao;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.follows.entity.Follow;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * follows 테이블을 조회/변경하는 MyBatis DAO
 *
 * TODO: SQL은 기존 UserMapper/PostDao.xml 방식에 맞춰 XML 매퍼에서 작성
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

	/** userId 를 팔로우하는 사용자 수 */
	int countFollowers(@Param("userId") Integer userId);

	/** userId 가 팔로우 중인 사용자 수 */
	int countFollowing(@Param("userId") Integer userId);

	List<FollowUserResponse> selectFollowers(
		@Param("viewerUserId") Integer viewerUserId,
		@Param("userId") Integer userId,
		@Param("offset") int offset,
		@Param("size") int size
	);

	List<FollowUserResponse> selectFollowing(
		@Param("viewerUserId") Integer viewerUserId,
		@Param("userId") Integer userId,
		@Param("offset") int offset,
		@Param("size") int size
	);
}
