package com.bizteam3.server.save.dao;

import java.util.List;

import com.bizteam3.server.post.entity.Post;
import com.bizteam3.server.save.entity.Save;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SaveDao {
    int insert(Save save);

    boolean existsByUserIdAndPostId(
        @Param("userId") Integer userId,
        @Param("postId") Integer postId
    );

    int deleteByUserIdAndPostId(
        @Param("userId") Integer userId,
        @Param("postId") Integer postId
    );

    int isSaved(
            @Param("userId") Integer userId,
            @Param("postId") Integer postId
    );

    List<Post> selectFeedPostsByUserId(
        @Param("userId") Integer userId,
        @Param("offset") int offset,
        @Param("size") int size
    );

    int countAllByUserId(
        @Param("userId") Integer userId
    );

}
