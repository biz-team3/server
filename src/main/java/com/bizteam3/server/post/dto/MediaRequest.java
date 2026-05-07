package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.MediaType;
import lombok.Data;

@Data
public class MediaRequest {
    MediaType mediaType; //enum 2가지-img, video
    String mediaUrl;
    Integer sortOrder;
    String originalFilename;
}
