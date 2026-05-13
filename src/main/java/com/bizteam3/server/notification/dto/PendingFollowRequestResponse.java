package com.bizteam3.server.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PendingFollowRequestResponse {

    private final Integer requestId;
    private final Integer requesterId;
    private final Integer targetUserId;
    private final String requesterName;
    /** 공통 팔로워 문구 구성에 사용할 대표 username임. */
    private final String mutualFollowerName;
    /** 요청자와 연결되는 공통 팔로워 수임. */
    private final Integer mutualFollowerCount;
    private final String requesterProfileImg;
    private final LocalDateTime createdAt;
}
