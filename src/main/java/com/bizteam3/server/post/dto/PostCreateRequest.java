package com.bizteam3.server.post.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PostCreateRequest {
    List<MediaRequest> media;
    Integer userId;
    String caption;
    String translatedCaption;
}
