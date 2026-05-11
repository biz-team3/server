package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    int insert(Post post);
    int selectUserId(Integer postId);
    int updateCaption(Post post);
    int countByPostId(Integer postId);

    int deleteMediasByPostId(Integer postId);
    int deletePostHashtagsByPostId(Integer postId);
    int delete(Integer postId);
}
