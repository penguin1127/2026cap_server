package com.expansion.server.domain.gallery.dto;

import com.expansion.server.domain.gallery.entity.GalleryPost;
import com.expansion.server.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 갤러리 목록 조회 시 사용하는 요약 DTO (카드 뷰용)
 */
@Getter
@Builder
public class GalleryPostSummary {

    private Long postId;
    private String title;
    private String thumbnailUrl;
    private Long authorId;
    private String authorNickname;
    private String authorProfileImageUrl;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private String galleryType;
    private String visibility;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GalleryPostSummary of(GalleryPost post, Profile profile, List<String> tags) {
        return GalleryPostSummary.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .authorId(post.getUser().getUserId())
                .authorNickname(profile != null ? profile.getNickname() : null)
                .authorProfileImageUrl(profile != null ? profile.getProfileImageUrl() : null)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .galleryType(post.getGalleryType().name())
                .visibility(post.getVisibility().name())
                .tags(tags != null ? tags : List.of())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
