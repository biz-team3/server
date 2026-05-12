package com.bizteam3.server.notification.dto;

import java.time.LocalDateTime;

import com.bizteam3.server.follows.dto.FollowViewerRelation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {
    /** 알림 식별자임. */
    private final Integer notificationId;

    /** 알림을 발생시킨 사용자 id임. follow / unfollow 요청 대상 식별에 사용함. */
    private final Integer actorUserId;

    /** 알림을 발생시킨 사용자 username임. 프로필 이동 경로에 사용함. */
    private final String actorUsername;

    /** 알림 타입임. 예: LIKE, FOLLOW */
    private final String type;

    /** 알림 문구에 표시할 사용자명임. 현재는 username 기준으로 내려줌. */
    private final String actorName;

    /** 알림을 발생시킨 사용자 프로필 이미지 경로임. */
    private final String actorImageUrl;

    /** 좋아요 알림 등 집계형 문구에 사용할 사용자 수임. */
    private final Integer actorCount;

    /** 게시글 썸네일 등 알림 우측에 표시할 대상 이미지 경로임. */
    private final String targetImageUrl;

    /** 현재 로그인 사용자가 actor를 어떤 상태로 보고 있는지 나타내는 값임. */
    private final FollowViewerRelation viewerRelation;

    /** 읽음 여부임. */
    private final Boolean read;

    /** 알림 생성 시각임. */
    private final LocalDateTime createdAt;
}
