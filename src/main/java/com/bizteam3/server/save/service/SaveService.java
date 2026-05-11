package com.bizteam3.server.save.service;

public interface SaveService {
    void save(Integer postId, Integer userId);
    void unsave(Integer postId, Integer userId);
}
