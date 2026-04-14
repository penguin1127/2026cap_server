package com.expansion.server.domain.asset.repository;

import com.expansion.server.domain.asset.entity.AssetPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetPurchaseRepository extends JpaRepository<AssetPurchase, Long> {

    boolean existsByUser_UserIdAndAsset_AssetId(Long userId, Long assetId);

    List<AssetPurchase> findByUser_UserId(Long userId);
}
