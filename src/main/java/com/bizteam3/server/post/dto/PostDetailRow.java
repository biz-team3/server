package com.bizteam3.server.post.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailRow {
    private Integer postId;
    private Integer authorUserId;
    private String authorUsername;
    private String authorProfileImageUrl;
    private AccountVisType authorAccountVis;
    private String caption;
    private String translatedCaption;
    private LocalDateTime createdAt;

}
