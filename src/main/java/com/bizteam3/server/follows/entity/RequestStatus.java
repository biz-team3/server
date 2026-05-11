package com.bizteam3.server.follows.entity;

/**
 * follow_requests 테이블의 요청 처리 상태를 나타내는 enum
 *
 * - PENDING  : 수신자가 아직 수락/거절하지 않은 대기 중 상태
 * - ACCEPTED : 수신자가 수락 → follows 테이블에 관계가 생성됨
 * - REJECTED : 수신자가 거절 → 아무 관계도 생성되지 않음
 */
public enum RequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
