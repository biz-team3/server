package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MediaRequest {
    @JsonProperty("type")
    MediaType mediaType; //enum 2가지-img, video
    @JsonProperty("url")
    String mediaUrl;
//    Integer sortOrder;
    String originalFileName;
}
