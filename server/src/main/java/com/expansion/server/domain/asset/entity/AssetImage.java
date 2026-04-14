package com.expansion.server.domain.asset.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asset_images")
@Getter
@NoArgsConstructor
public class AssetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public AssetImage(Asset asset, String imageUrl, int sortOrder) {
        this.asset = asset;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }
}
