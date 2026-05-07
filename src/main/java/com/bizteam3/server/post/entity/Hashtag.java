package com.bizteam3.server.post.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {
    Integer hashtagId;
    String hashtagName;
    LocalDateTime createdAt;

}
