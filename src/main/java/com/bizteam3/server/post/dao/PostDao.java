package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDao {
    int insert(Post post);
}
