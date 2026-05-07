package com.bizteam3.server.post.dto;

import lombok.Data;

@Data
public class PostCreateRequest {
//    List<Media> medias;
    Integer userId;
    String caption;
    String translatedCaption;
//    List<HashTag> hashTags;.
//    String isSuggested;
}
