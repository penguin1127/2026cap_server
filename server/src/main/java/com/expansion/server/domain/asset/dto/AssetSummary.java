package com.expansion.server.domain.asset.dto;

import com.expansion.server.domain.asset.entity.Asset;
import com.expansion.server.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AssetSummary {

    private Long assetId;
    private String title;
    private String thumbnailUrl;
    private Long authorId;
    private String authorNickname;
    private String authorProfileImageUrl;
    private BigDecimal price;
    private boolean isFree;
    private int downloadCount;
    private int likeCount;
    private int commentCount;
    private String status;
    private LocalDateTime createdAt;

    public static AssetSummary of(Asset asset, Profile profile) {
        return AssetSummary.builder()
                .assetId(asset.getAssetId())
                .title(asset.getTitle())
                .thumbnailUrl(asset.getThumbnailUrl())
                .authorId(asset.getUser().getUserId())
                .authorNickname(profile.getNickname())
                .authorProfileImageUrl(profile.getProfileImageUrl())
                .price(asset.getPrice())
                .isFree(asset.isFree())
                .downloadCount(asset.getDownloadCount())
                .likeCount(asset.getLikeCount())
                .commentCount(asset.getCommentCount())
                .status(asset.getStatus())
                .createdAt(asset.getCreatedAt())
                .build();
    }
}
