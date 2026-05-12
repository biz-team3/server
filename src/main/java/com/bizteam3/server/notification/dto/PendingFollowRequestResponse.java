package com.bizteam3.server.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PendingFollowRequestResponse {

    private final Integer requestId;
    private final Integer requesterId;
    private final Integer targetUserId;
    private final String requesterName;
    private final String mutualText;
    private final String requesterProfileImg;
}
