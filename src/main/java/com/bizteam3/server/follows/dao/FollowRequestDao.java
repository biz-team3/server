package com.bizteam3.server.follows.dao;

import java.util.List;

import com.bizteam3.server.follows.dto.FollowRequestResponse;
import com.bizteam3.server.follows.entity.FollowRequest;
import com.bizteam3.server.notification.dto.PendingFollowRequestResponse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * follow_requests 테이블을 조회/변경하는 MyBatis DAO
 * SQL은 FollowRequestDao.xml 에 작성
 */
@Mapper
public interface FollowRequestDao {

    /** 팔로우 요청 생성 (PENDING) */
    int insert(FollowRequest followRequest);

    /**
     * 요청 상태 업데이트 (PENDING → ACCEPTED / REJECTED)
     *
     * @param requestId  업데이트할 요청 PK
     * @param status     변경할 상태값 (문자열 "ACCEPTED" / "REJECTED")
     */
    int updateStatus(
        @Param("requestId") Integer requestId,
        @Param("status")    String  status
    );

    int updatePendingStatusByUsers(
        @Param("requesterUserId") Integer requesterUserId,
        @Param("receiverUserId") Integer receiverUserId,
        @Param("status") String status
    );

    /**
     * 두 사용자 간 PENDING 요청 존재 여부 확인 (중복 요청 방지)
     *
     * @param requesterUserId 요청자 ID
     * @param receiverUserId  수신자 ID
     */
    int countPending(
        @Param("requesterUserId") Integer requesterUserId,
        @Param("receiverUserId")  Integer receiverUserId
    );

    /**
     * 요청 단건 조회 (수락/거절 시 소유권 검증에 사용)
     *
     * @param requestId 요청 PK
     */
    FollowRequest selectById(@Param("requestId") Integer requestId);

    /**
     * 나에게 온 PENDING 팔로우 요청 목록 조회
     *
     * @param receiverUserId 수신자(비공개 계정 본인) ID
     */
    List<FollowRequestResponse> selectPendingByReceiver(@Param("receiverUserId") Integer receiverUserId);

    /**
     * 알림 패널용 PENDING 팔로우 요청 목록 조회
     *
     * @param receiverUserId 수신자(비공개 계정 본인) ID
     */
    List<PendingFollowRequestResponse> selectPendingNotificationByReceiver(
        @Param("receiverUserId") Integer receiverUserId
    );

    /** 특정 사용자가 받은 PENDING 요청 개수 */
    int countPendingByReceiver(@Param("receiverUserId") Integer receiverUserId);
}
