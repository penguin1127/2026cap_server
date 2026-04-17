package com.expansion.server.domain.editor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LayerSaveRequest {

    private Long layerId;

    @NotBlank
    @Size(max = 50)
    private String name;

    private int layerOrder;

    private String blendMode;

    private boolean isLocked;

    private boolean isVisible;

    private float opacity;

    private String fileUrl;

    private String pixelData;
}
