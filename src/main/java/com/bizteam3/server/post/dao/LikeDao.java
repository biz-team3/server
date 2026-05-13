package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeDao {
    int insert(Like like);
    int isLiked(
            @Param("userId") Integer userId,
            @Param("postId") Integer postId
    );
    int delete(Like like);
    int countByPostId(@Param("postId") Integer postId);

    int countAllByPostId(@Param("postId") Integer postId);
}
