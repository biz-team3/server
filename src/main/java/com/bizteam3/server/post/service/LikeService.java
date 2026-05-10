package com.bizteam3.server.post.service;

public interface LikeService {
    void likePost(Integer userId, Integer postId);
    void unlikePost(Integer userId, Integer postId);
}
