package com.expansion.server.domain.editor.dto;

import com.expansion.server.domain.editor.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProjectResponse {

    private Long projectId;
    private String title;
    private int width;
    private int height;
    private String backgroundColor;
    private String fileUrl;
    private String thumbnailUrl;
    private boolean isPublic;
    private String status;
    private boolean aiAnalyzed;
    private List<LayerResponse> layers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectResponse of(Project project, List<LayerResponse> layers) {
        return ProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .width(project.getWidth())
                .height(project.getHeight())
                .backgroundColor(project.getBackgroundColor())
                .fileUrl(project.getFileUrl())
                .thumbnailUrl(project.getThumbnailUrl())
                .isPublic(project.isPublic())
                .status(project.getStatus())
                .aiAnalyzed(project.isAiAnalyzed())
                .layers(layers)
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
