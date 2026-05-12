package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostMediaResponse {
    Integer mediaId;
    @JsonProperty("type")
    MediaType mediaType; //enum 2가지-img, video
    @JsonProperty("url")
    String mediaUrl;
    Integer sortOrder;
}
