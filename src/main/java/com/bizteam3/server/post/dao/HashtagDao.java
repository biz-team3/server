package com.bizteam3.server.post.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface HashtagDao {
    int insertHashtag(@Param("hashtagSet") Set<String> hashtagSet);
    int insertPostHashtag(
            @Param("postId") Integer postId,
            @Param("hashtagSet") Set<String> hashtagSet
    );
    int deletePostHashtagByPostId(@Param("postId") Integer postId);
}
