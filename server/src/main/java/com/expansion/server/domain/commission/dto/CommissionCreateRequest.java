package com.expansion.server.domain.commission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 계약 성사(Commission) 생성 요청
 * 의뢰자가 작가를 선택하여 계약을 확정할 때 사용
 */
@Getter
@NoArgsConstructor
public class CommissionCreateRequest {

    @NotNull
    private String commissionType;  // SERVICE_OPTION / SERVICE_QUOTE / REQUEST

    @NotNull
    private Long artistId;

    private Long serviceId;
    private Long requestPostId;
    private Long applicationId;

    @NotNull
    private BigDecimal agreedPrice;

    private LocalDate agreedDeadline;
}
