package com.bizteam3.server.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class MediaReplaceRequest {
    List<MediaRequest> media;
}
