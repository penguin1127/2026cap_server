package com.expansion.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BlockResponse {

    private List<Long> blockedUserIds;
    private List<String> blockedTags;
}
