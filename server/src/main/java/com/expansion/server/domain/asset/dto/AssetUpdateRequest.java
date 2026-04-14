package com.expansion.server.domain.asset.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class AssetUpdateRequest {

    private String title;

    private String description;

    private String thumbnailUrl;

    private BigDecimal price;

    private Boolean isFree;

    private Long categoryId;

    private Long licenseTypeId;

    private List<String> imageUrls;

    private List<String> tags;
}
