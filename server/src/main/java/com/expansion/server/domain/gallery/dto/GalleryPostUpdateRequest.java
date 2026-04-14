package com.expansion.server.domain.gallery.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GalleryPostUpdateRequest {

    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    private String thumbnailUrl;

    // "PUBLIC" | "PRIVATE" | "UNLISTED"
    private String visibility;

    private Long categoryId;

    private Boolean isEditable;

    private List<String> imageUrls;

    private List<String> tags;
}
