package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.dao.row.FeedPostMediaRow;
import com.bizteam3.server.post.entity.Media;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MediaDao {
    int insert(Media media);
    int deleteByPostId(Integer postId);
    List<FeedPostMediaRow> selectByPostIds(@Param("postIds") List<Integer> postIds);
}
