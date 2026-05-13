package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.dto.vo.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    Integer postId;
    PostAuthorResponse author;
    List<PostMediaResponse> media;
    String caption;
    String translatedCaption;
    LocalDateTime createdAt;
    Integer likeCount;
    Integer commentCount;
    Boolean likedByMe;
    Boolean savedByMe;
    Boolean isOwner;
}
