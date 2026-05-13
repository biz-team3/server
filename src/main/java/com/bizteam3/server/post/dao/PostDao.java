package com.bizteam3.server.post.dao;

import com.bizteam3.server.post.dao.row.FeedPostRow;
import com.bizteam3.server.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostDao {
    int insert(Post post);
    int selectUserId(Integer postId);
    boolean existsByPostId(Integer postId);
    int updateCaption(Post post);
    int countByPostId(Integer postId);

    int deleteMediasByPostId(Integer postId);
    int deletePostHashtagsByPostId(Integer postId);
    int delete(Integer postId);

    int countFeedPosts(@Param("viewerId") Integer viewerId);

    List<FeedPostRow> selectFeedPosts(
            @Param("viewerId") Integer viewerId,
            @Param("offset") int offset,
            @Param("size") int size
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
