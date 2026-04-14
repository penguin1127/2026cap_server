package com.expansion.server.domain.gallery.dto;

import com.expansion.server.domain.gallery.entity.GalleryComment;
import com.expansion.server.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GalleryCommentResponse {

    private Long commentId;
    private Long postId;
    private Long parentId;

    private Long authorId;
    private String authorNickname;
    private String authorProfileImageUrl;

    private String content;
    private boolean isDeleted;
    private int replyCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GalleryCommentResponse of(GalleryComment comment, Profile profile) {
        return GalleryCommentResponse.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .parentId(comment.getParent() != null ? comment.getParent().getCommentId() : null)
                .authorId(comment.getUser().getUserId())
                .authorNickname(profile != null ? profile.getNickname() : null)
                .authorProfileImageUrl(profile != null ? profile.getProfileImageUrl() : null)
                .content(comment.getContent())
                .isDeleted(comment.isDeleted())
                .replyCount(comment.getReplies().size())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
