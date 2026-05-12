package com.bizteam3.server.follows.dto;

/**
 * 팔로워/팔로잉 목록에서 현재 조회자 기준으로 표시할 관계 상태
 */
public enum FollowViewerRelation {
    SELF,
    FOLLOWING,
    PENDING,
    NOT_FOLLOWING
}
