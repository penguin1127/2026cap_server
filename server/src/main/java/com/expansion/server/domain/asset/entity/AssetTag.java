package com.expansion.server.domain.asset.entity;

import com.expansion.server.domain.common.entity.Tag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "asset_tags",
        uniqueConstraints = @UniqueConstraint(columnNames = {"asset_id", "tag_id"})
)
@Getter
@NoArgsConstructor
public class AssetTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_tag_id")
    private Long assetTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public AssetTag(Asset asset, Tag tag) {
        this.asset = asset;
        this.tag = tag;
    }
}
