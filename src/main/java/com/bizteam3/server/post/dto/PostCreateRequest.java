package com.bizteam3.server.post.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostCreateRequest {
    Integer userId;
    String caption;
    String translatedCaption;
}
