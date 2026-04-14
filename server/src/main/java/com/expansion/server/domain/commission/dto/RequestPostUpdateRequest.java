package com.expansion.server.domain.commission.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RequestPostUpdateRequest {

    @Size(max = 100)
    private String title;

    private String description;

    private BigDecimal budgetMin;

    private BigDecimal budgetMax;

    private LocalDate deadline;
}
