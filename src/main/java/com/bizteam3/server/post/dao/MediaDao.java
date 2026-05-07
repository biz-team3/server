package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.entity.Media;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MediaDao {
    int insert(Media media);
}
