package com.expansion.server.domain.commission.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommissionUpdateRequest {

    // IN_PROGRESS / REVIEW / COMPLETED / CANCELLED
    private String status;
}
