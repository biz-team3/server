package com.bizteam3.server.follows.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * follow_requests 테이블과 매핑되는 팔로우 요청 엔티티
 *
 * 비공개 계정에 팔로우할 경우 follows 테이블 대신 이 테이블에 PENDING 상태로 삽입됨.
 * 수신자가 수락하면 ACCEPTED 로 업데이트 + follows 테이블에 실제 관계 생성.
 * 거절하면 REJECTED 로 업데이트.
 */
@Data
@NoArgsConstructor
public class FollowRequest {

    private Integer requestId;
    private Integer requesterUserId;   // 팔로우 요청을 보낸 사람
    private Integer receiverUserId;    // 팔로우 요청을 받은 사람 (비공개 계정)
    private RequestStatus status;      // PENDING / ACCEPTED / REJECTED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FollowRequest(Integer requesterUserId, Integer receiverUserId) {
        this.requesterUserId = requesterUserId;
        this.receiverUserId  = receiverUserId;
        this.status          = RequestStatus.PENDING;
    }
}
