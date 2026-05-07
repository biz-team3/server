package com.bizteam3.server.post.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostMedia {
    Integer mediaId;
    Integer postId;
//    MediaType mediaType; //enum 2가지-img video
    String mediaUrl;
    Integer sortOrder;
    String originalFilename;
//    String contentType; //for what?
    Integer fileSize;
    LocalDateTime createdAt;
}
