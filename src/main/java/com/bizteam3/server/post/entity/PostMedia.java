package com.bizteam3.server.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMedia {
    private Integer mediaId;
    private Integer postId;
    private String type;
    private String url;
    private Integer sortOrder;
    private String originalFileName;
}
