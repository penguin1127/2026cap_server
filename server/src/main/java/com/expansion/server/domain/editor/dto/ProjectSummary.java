package com.expansion.server.domain.editor.dto;

import com.expansion.server.domain.editor.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectSummary {

    private Long projectId;
    private String title;
    private String thumbnailUrl;
    private int width;
    private int height;
    private boolean isPublic;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectSummary of(Project project) {
        return ProjectSummary.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .thumbnailUrl(project.getThumbnailUrl())
                .width(project.getWidth())
                .height(project.getHeight())
                .isPublic(project.isPublic())
                .status(project.getStatus())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
