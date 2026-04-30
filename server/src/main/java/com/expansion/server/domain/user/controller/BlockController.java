package com.expansion.server.domain.user.controller;

import com.expansion.server.domain.user.dto.BlockResponse;
import com.expansion.server.domain.user.service.BlockService;
import com.expansion.server.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    private Long resolveUserId(Long principal) {
        if (principal != null) return principal;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Long id) return id;
        return null;
    }

    /**
     * GET /api/blocks
     * 내 차단 목록 전체 조회 (로그인 필수)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<BlockResponse>> getMyBlocks(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(
                blockService.getMyBlocks(resolveUserId(userId))));
    }

    /**
     * POST /api/blocks/users/{targetUserId}
     * 사용자 차단 (로그인 필수)
     */
    @PostMapping("/users/{targetUserId}")
    public ResponseEntity<ApiResponse<Void>> blockUser(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long targetUserId) {
        blockService.blockUser(resolveUserId(userId), targetUserId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    /**
     * DELETE /api/blocks/users/{targetUserId}
     * 사용자 차단 해제 (로그인 필수)
     */
    @DeleteMapping("/users/{targetUserId}")
    public ResponseEntity<ApiResponse<Void>> unblockUser(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long targetUserId) {
        blockService.unblockUser(resolveUserId(userId), targetUserId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    /**
     * POST /api/blocks/tags/{tagName}
     * 태그 차단 (로그인 필수)
     */
    @PostMapping("/tags/{tagName}")
    public ResponseEntity<ApiResponse<Void>> blockTag(
            @AuthenticationPrincipal Long userId,
            @PathVariable String tagName) {
        blockService.blockTag(resolveUserId(userId), tagName);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    /**
     * DELETE /api/blocks/tags/{tagName}
     * 태그 차단 해제 (로그인 필수)
     */
    @DeleteMapping("/tags/{tagName}")
    public ResponseEntity<ApiResponse<Void>> unblockTag(
            @AuthenticationPrincipal Long userId,
            @PathVariable String tagName) {
        blockService.unblockTag(resolveUserId(userId), tagName);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
