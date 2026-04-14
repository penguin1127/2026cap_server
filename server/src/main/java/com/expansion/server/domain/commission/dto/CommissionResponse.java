package com.expansion.server.domain.commission.dto;

import com.expansion.server.domain.commission.entity.Commission;
import com.expansion.server.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class CommissionResponse {

    private Long commissionId;
    private String commissionType;

    private Long clientId;
    private String clientNickname;

    private Long artistId;
    private String artistNickname;

    private Long serviceId;
    private Long requestPostId;
    private Long applicationId;
    private Long paymentId;

    private BigDecimal agreedPrice;
    private LocalDate agreedDeadline;

    private String status;
    private String fileUrl;
    private LocalDateTime completedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommissionResponse of(Commission c, Profile clientProfile, Profile artistProfile) {
        return CommissionResponse.builder()
                .commissionId(c.getCommissionId())
                .commissionType(c.getCommissionType())
                .clientId(c.getClient().getUserId())
                .clientNickname(clientProfile != null ? clientProfile.getNickname() : null)
                .artistId(c.getArtist().getUserId())
                .artistNickname(artistProfile != null ? artistProfile.getNickname() : null)
                .serviceId(c.getServiceId())
                .requestPostId(c.getRequestPostId())
                .applicationId(c.getApplicationId())
                .paymentId(c.getPaymentId())
                .agreedPrice(c.getAgreedPrice())
                .agreedDeadline(c.getAgreedDeadline())
                .status(c.getStatus())
                .fileUrl(c.getFileUrl())
                .completedAt(c.getCompletedAt())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}
