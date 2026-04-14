package com.expansion.server.domain.commission.dto;

import com.expansion.server.domain.commission.entity.RequestPost;
import com.expansion.server.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class RequestPostResponse {

    private Long requestPostId;
    private String title;
    private String description;

    private Long clientId;
    private String clientNickname;
    private String clientProfileImageUrl;

    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private LocalDate deadline;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RequestPostResponse of(RequestPost post, Profile profile) {
        return RequestPostResponse.builder()
                .requestPostId(post.getRequestPostId())
                .title(post.getTitle())
                .description(post.getDescription())
                .clientId(post.getClient().getUserId())
                .clientNickname(profile != null ? profile.getNickname() : null)
                .clientProfileImageUrl(profile != null ? profile.getProfileImageUrl() : null)
                .budgetMin(post.getBudgetMin())
                .budgetMax(post.getBudgetMax())
                .deadline(post.getDeadline())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
