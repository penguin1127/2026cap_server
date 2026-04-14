package com.expansion.server.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequest {

    @Pattern(regexp = "^[a-z0-9_]{4,20}$",
            message = "닉네임은 영소문자, 숫자, 언더스코어만 사용 가능하며 4~20자여야 합니다.")
    private String nickname;

    @Size(max = 255, message = "소개는 255자 이하로 입력해주세요.")
    private String bio;

    @Size(max = 255, message = "웹사이트 URL은 255자 이하로 입력해주세요.")
    private String websiteUrl;

    @Size(max = 500, message = "프로필 이미지 URL은 500자 이하로 입력해주세요.")
    private String profileImageUrl;

    private Boolean isPublic;
}
