package com.bizteam3.server.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostCreateRequest {
    List<MediaRequest> media;
    Integer userId;
    String caption;
    String translatedCaption;
//    List<HashTag> hashTags;.
//    String isSuggested;
}
