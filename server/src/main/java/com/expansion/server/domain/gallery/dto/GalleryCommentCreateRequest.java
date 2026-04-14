package com.expansion.server.domain.gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GalleryCommentCreateRequest {

    @NotBlank
    @Size(max = 1000)
    private String content;

    // null이면 최상위 댓글, 값이 있으면 대댓글
    private Long parentId;
}
