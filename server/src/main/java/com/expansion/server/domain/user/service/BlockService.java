package com.expansion.server.domain.user.service;

import com.expansion.server.domain.user.dto.BlockResponse;
import com.expansion.server.domain.user.entity.User;
import com.expansion.server.domain.user.entity.UserBlock;
import com.expansion.server.domain.user.repository.UserBlockRepository;
import com.expansion.server.domain.user.repository.UserRepository;
import com.expansion.server.global.exception.CustomException;
import com.expansion.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService {

    private static final String TYPE_USER = "USER";
    private static final String TYPE_TAG  = "TAG";

    private final UserBlockRepository userBlockRepository;
    private final UserRepository      userRepository;

    // ──────────────────────────────────────────────
    // 차단 목록 조회
    // ──────────────────────────────────────────────

    public BlockResponse getMyBlocks(Long userId) {
        List<UserBlock> blocks = userBlockRepository.findByUser_UserId(userId);

        List<Long> blockedUserIds = blocks.stream()
                .filter(b -> TYPE_USER.equals(b.getBlockType()))
                .map(b -> b.getTargetUser().getUserId())
                .toList();

        List<String> blockedTags = blocks.stream()
                .filter(b -> TYPE_TAG.equals(b.getBlockType()))
                .map(UserBlock::getTargetTag)
                .toList();

        return BlockResponse.builder()
                .blockedUserIds(blockedUserIds)
                .blockedTags(blockedTags)
                .build();
    }

    // ──────────────────────────────────────────────
    // 사용자 차단
    // ──────────────────────────────────────────────

    @Transactional
    public void blockUser(Long userId, Long targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // 이미 차단 중이면 무시
        boolean exists = userBlockRepository
                .findByUser_UserIdAndBlockTypeAndTargetUser_UserId(userId, TYPE_USER, targetUserId)
                .isPresent();
        if (exists) return;

        User user       = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            userBlockRepository.save(UserBlock.builder()
                    .user(user)
                    .blockType(TYPE_USER)
                    .targetUser(targetUser)
                    .build());
        } catch (DataIntegrityViolationException ignored) {
            // 동시 요청으로 인한 중복 저장 시도 — 무시
        }
    }

    @Transactional
    public void unblockUser(Long userId, Long targetUserId) {
        userBlockRepository.deleteByUser_UserIdAndBlockTypeAndTargetUser_UserId(
                userId, TYPE_USER, targetUserId);
    }

    // ──────────────────────────────────────────────
    // 태그 차단
    // ──────────────────────────────────────────────

    @Transactional
    public void blockTag(Long userId, String tagName) {
        // 태그 입력값 검증
        String trimmed = (tagName == null) ? "" : tagName.trim();
        if (trimmed.isEmpty() || trimmed.length() > 100) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // 이미 차단 중이면 무시
        boolean exists = userBlockRepository
                .findByUser_UserIdAndBlockTypeAndTargetTag(userId, TYPE_TAG, trimmed)
                .isPresent();
        if (exists) return;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            userBlockRepository.save(UserBlock.builder()
                    .user(user)
                    .blockType(TYPE_TAG)
                    .targetTag(trimmed)
                    .build());
        } catch (DataIntegrityViolationException ignored) {
            // 동시 요청으로 인한 중복 저장 시도 — 무시
        }
    }

    @Transactional
    public void unblockTag(Long userId, String tagName) {
        String trimmed = (tagName == null) ? "" : tagName.trim();
        userBlockRepository.deleteByUser_UserIdAndBlockTypeAndTargetTag(
                userId, TYPE_TAG, trimmed);
    }
}
