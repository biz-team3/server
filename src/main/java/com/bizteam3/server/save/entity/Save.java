package com.bizteam3.server.save.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Save {
    Integer saveId;
    Integer userId;
    Integer postId;
    LocalDateTime createdAt;

    public Save(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
