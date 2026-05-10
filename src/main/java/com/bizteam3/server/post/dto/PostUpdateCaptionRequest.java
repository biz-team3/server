package com.bizteam3.server.post.dto;

import lombok.Data;

@Data
public class PostUpdateCaptionRequest {
    String caption;
    String translatedCaption;
}
