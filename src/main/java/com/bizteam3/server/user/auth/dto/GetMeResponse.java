package com.bizteam3.server.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMeResponse {
    private Integer userId;
    private String username;
    private String name;
    private String profileImageUrl;
}
