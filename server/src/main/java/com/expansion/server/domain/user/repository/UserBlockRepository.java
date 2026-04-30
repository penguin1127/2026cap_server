package com.expansion.server.domain.user.repository;

import com.expansion.server.domain.user.entity.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    /** 내 차단 목록 전체 조회 */
    List<UserBlock> findByUser_UserId(Long userId);

    /** 사용자 차단 여부 확인 */
    Optional<UserBlock> findByUser_UserIdAndBlockTypeAndTargetUser_UserId(
            Long userId, String blockType, Long targetUserId);

    /** 태그 차단 여부 확인 */
    Optional<UserBlock> findByUser_UserIdAndBlockTypeAndTargetTag(
            Long userId, String blockType, String targetTag);

    /** 사용자 차단 해제 */
    void deleteByUser_UserIdAndBlockTypeAndTargetUser_UserId(
            Long userId, String blockType, Long targetUserId);

    /** 태그 차단 해제 */
    void deleteByUser_UserIdAndBlockTypeAndTargetTag(
            Long userId, String blockType, String targetTag);
}
