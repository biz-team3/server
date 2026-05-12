package com.bizteam3.server.notification.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarkNotificationsReadRequest {
    private List<Integer> notificationIds = new ArrayList<>();
}
