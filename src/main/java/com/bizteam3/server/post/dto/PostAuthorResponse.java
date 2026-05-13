package com.bizteam3.server.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAuthorResponse {
    private Integer userId;
    private String username;
    private String profileImageUrl;
}
