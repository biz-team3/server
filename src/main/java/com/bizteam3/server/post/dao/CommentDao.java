package com.bizteam3.server.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bizteam3.server.post.dao.row.CommentListRow;
import com.bizteam3.server.post.entity.Comment;

@Mapper
public interface CommentDao {

	int insert(Comment comment);

	int update(Comment comment);

	int delete(@Param("commentId") Integer commentId);

	List<CommentListRow> selectPage(
		@Param("postId") Integer postId,
		@Param("offset") int offset,
		@Param("size") int size
	);

	int countAllByPostId(@Param("postId") Integer postId);
}
