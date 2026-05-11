package com.bizteam3.server.save.dao;

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

}
