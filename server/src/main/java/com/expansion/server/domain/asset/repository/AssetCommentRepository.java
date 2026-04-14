package com.expansion.server.domain.asset.repository;

import com.expansion.server.domain.asset.entity.AssetComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetCommentRepository extends JpaRepository<AssetComment, Long> {

    Page<AssetComment> findByAsset_AssetIdAndParentIsNull(Long assetId, Pageable pageable);
}
