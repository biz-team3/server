package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.entity.Post;
import com.bizteam3.server.post.entity.PostMedia;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    int insertPost(Post post);
    int insertPostMedia(PostMedia postMedia);
    // Optional: Batch insert for media
    int insertPostMediaList(List<PostMedia> mediaList);
}
