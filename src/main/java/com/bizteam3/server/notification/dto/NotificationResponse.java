package com.bizteam3.server.notification.dto;

import java.time.LocalDateTime;

import com.bizteam3.server.follows.dto.FollowViewerRelation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {
    /** 알림 식별자 */
    private final Integer notificationId;

    /** 프론트 follow / unfollow 요청 대상 식별에 사용함. */
    private final Integer actorUserId;

    /** 알림을 발생시킨 사용자 username. 프론트에서 /profile/{username} 이동 경로에 사용함. */
    private final String actorUsername;

    /** 알림 타입. 예: LIKE, FOLLOW */
    private final String type;

    /** 알림 문구에 표시할 사용자명. 현재는 username 기준으로 내려줌. */
    private final String actorName;

    private final String actorImageUrl;

    private final Integer actorCount;

    private final String targetImageUrl;

    /** 현재 로그인 사용자가 actor를 어떤 상태로 보고 있는지 나타내는 값. */
    private final FollowViewerRelation viewerRelation;

    private final Boolean read;
    private final LocalDateTime createdAt;
}
