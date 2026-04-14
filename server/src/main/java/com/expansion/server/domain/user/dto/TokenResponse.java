package com.expansion.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;   // "Bearer"
    private long expiresIn;     // 초 단위 (access token 만료까지)
}
