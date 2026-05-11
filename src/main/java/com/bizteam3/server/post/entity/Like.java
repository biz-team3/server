package com.bizteam3.server.post.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    Integer likeId;
    Integer userId;
    Integer postId;
    LocalDateTime createdAt;

    public Like(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
