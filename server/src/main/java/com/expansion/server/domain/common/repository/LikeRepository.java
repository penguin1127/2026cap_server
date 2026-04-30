package com.expansion.server.domain.common.repository;

import com.expansion.server.domain.common.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUser_UserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);

    Optional<Like> findByUser_UserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);

    long countByTargetIdAndTargetType(Long targetId, String targetType);

    // 유저가 좋아요한 targetType 목록 (페이징)
    Page<Like> findByUser_UserIdAndTargetType(Long userId, String targetType, Pageable pageable);

    // 유저가 좋아요한 targetType 목록 (전체 — targetId 추출용)
    List<Like> findByUser_UserIdAndTargetType(Long userId, String targetType);
}
