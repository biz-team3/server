package com.bizteam3.server.user.dto;

import lombok.Data;

@Data
public class UserRemoveResponse {
    private String result; // "success" 또는 "failure"
    private String message; // 처리 결과에 대한 상세 메시지
}
