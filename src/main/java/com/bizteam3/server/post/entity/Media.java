package com.bizteam3.server.post.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    Integer mediaId;
    Integer postId;
    MediaType mediaType; //enum 2가지-img, video
    String mediaUrl;
    Integer sortOrder;
    String originalFilename;
//    String contentType; //릴스, 스토리, 피드?
    Integer fileSize;
    LocalDateTime createdAt;

    public Media(MediaType mediaType, String mediaUrl, Integer sortOrder, String originalFilename){
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.sortOrder = sortOrder;
        this.originalFilename = originalFilename;
    }
}
