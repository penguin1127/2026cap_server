package com.expansion.server.domain.common.repository;

import com.expansion.server.domain.common.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUser_UserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);

    Optional<Like> findByUser_UserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);

    long countByTargetIdAndTargetType(Long targetId, String targetType);
}
