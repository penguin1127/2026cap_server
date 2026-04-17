package com.expansion.server.domain.commission.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CommissionApplicationCreateRequest {

    private Long requestPostId;

    private String message;

    private BigDecimal proposedPrice;
}
