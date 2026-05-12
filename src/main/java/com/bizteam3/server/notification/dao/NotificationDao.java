package com.bizteam3.server.notification.dao;

import java.util.List;

import com.bizteam3.server.notification.dto.NotificationResponse;
import com.bizteam3.server.notification.entity.Notification;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationDao {

    int insert(Notification notification);

    List<NotificationResponse> selectByReceiverUserId(@Param("receiverUserId") Integer receiverUserId);

    int countUnreadByReceiverUserId(@Param("receiverUserId") Integer receiverUserId);

    int markAllReadByReceiverUserId(@Param("receiverUserId") Integer receiverUserId);

    int markSelectedReadByReceiverUserId(
        @Param("receiverUserId") Integer receiverUserId,
        @Param("notificationIds") List<Integer> notificationIds
    );
}
