package com.expansion.server.domain.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssetCommentCreateRequest {

    @NotBlank
    @Size(max = 1000)
    private String content;

    private Long parentId;
}
