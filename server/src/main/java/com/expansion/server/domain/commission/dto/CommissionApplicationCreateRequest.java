package com.expansion.server.domain.commission.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CommissionApplicationCreateRequest {

    @NotNull(message = "의뢰 ID는 필수입니다.")
    private Long requestPostId;

    @Size(max = 2000, message = "메시지는 2000자 이하여야 합니다.")
    private String message;

    @DecimalMin(value = "0.00", message = "제안 금액은 0 이상이어야 합니다.")
    private BigDecimal proposedPrice;
}
