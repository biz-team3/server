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
    String originalFileName;
    String contentType; // Image/jpeg 같은 형식
    Integer fileSize; //파일이 클 경우, Long 변경
    LocalDateTime createdAt;

    public Media(MediaType mediaType, String mediaUrl, Integer sortOrder, String originalFileName){
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.sortOrder = sortOrder;
        this.originalFileName = originalFileName;
    }
}
