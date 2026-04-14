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
public class RequestPostSummary {

    private Long requestPostId;
    private String title;

    private Long clientId;
    private String clientNickname;

    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private LocalDate deadline;
    private String status;

    private LocalDateTime createdAt;

    public static RequestPostSummary of(RequestPost post, Profile profile) {
        return RequestPostSummary.builder()
                .requestPostId(post.getRequestPostId())
                .title(post.getTitle())
                .clientId(post.getClient().getUserId())
                .clientNickname(profile != null ? profile.getNickname() : null)
                .budgetMin(post.getBudgetMin())
                .budgetMax(post.getBudgetMax())
                .deadline(post.getDeadline())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
