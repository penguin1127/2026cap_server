package com.expansion.server.domain.asset.repository;

import com.expansion.server.domain.asset.entity.AssetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetImageRepository extends JpaRepository<AssetImage, Long> {

    List<AssetImage> findByAsset_AssetIdOrderBySortOrderAsc(Long assetId);

    void deleteByAsset_AssetId(Long assetId);
}
