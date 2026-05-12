package com.bizteam3.server.post.dao.row;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedPostRow {
    private Integer postId;

    private Integer userId;
    private String username;
    private String profileImageUrl;

    private String caption;
    private String translatedCaption;
    private LocalDateTime createdAt;

    private Integer likeCount;
    private Integer commentCount;

    private Boolean likedByMe;
    private Boolean savedByMe;
    private Boolean isOwner;
}
