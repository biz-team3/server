package com.bizteam3.server.notification.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PendingFollowRequestListResponse {

    private final List<PendingFollowRequestResponse> requests;

    private PendingFollowRequestListResponse(List<PendingFollowRequestResponse> requests) {
        this.requests = requests;
    }

    public static PendingFollowRequestListResponse of(List<PendingFollowRequestResponse> requests) {
        return new PendingFollowRequestListResponse(requests);
    }
}
