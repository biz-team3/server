package com.bizteam3.server.post.dao.row;

import com.bizteam3.server.post.entity.MediaType;
import lombok.Data;

@Data
public class FeedPostMediaRow {
    private Integer postId;
    private Integer mediaId;
    private MediaType mediaType;
    private String mediaUrl;
    private Integer sortOrder;
}
