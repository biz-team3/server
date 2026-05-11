package com.bizteam3.server.follows.dto;

import java.time.LocalDateTime;

import com.bizteam3.server.follows.entity.RequestStatus;

import lombok.Builder;
import lombok.Data;

/**
 * 팔로우 요청 목록 조회 시 반환되는 응답 DTO
 * - 수신자(비공개 계정 본인)가 받은 대기 중 요청 목록에 사용
 */
@Data
@Builder
public class FollowRequestResponse {
    private final Integer requestId;
    private final Integer requesterUserId;
    private final String  requesterName;
    private final String  requesterProfileImg;
    private final RequestStatus status;
    private final LocalDateTime requestedAt;
}
