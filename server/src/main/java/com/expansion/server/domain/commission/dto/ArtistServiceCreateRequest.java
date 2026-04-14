package com.expansion.server.domain.commission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ArtistServiceCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String serviceType;  // OPTION / QUOTE

    private BigDecimal basePrice;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Integer estimatedDays;
}
