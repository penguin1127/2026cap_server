package com.expansion.server.domain.user.controller;

import com.expansion.server.domain.user.dto.ProfileUpdateRequest;
import com.expansion.server.domain.user.dto.UserProfileResponse;
import com.expansion.server.domain.user.service.UserService;
import com.expansion.server.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /api/users/me
     * 내 프로필 조회 (로그인 필수)
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getMyProfile(userId)));
    }

    /**
     * PATCH /api/users/me
     * 내 프로필 수정 (로그인 필수)
     */
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.updateProfile(userId, request)));
    }

    /**
     * GET /api/users/by-nickname/{nickname}
     * 닉네임으로 유저 프로필 조회 (프로필 페이지용)
     */
    @GetMapping("/by-nickname/{nickname}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserByNickname(
            @PathVariable String nickname,
            @AuthenticationPrincipal Long currentUserId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getUserByNickname(nickname, currentUserId)));
    }

    /**
     * GET /api/users/{userId}
     * 특정 유저 프로필 조회
     * - 로그인 상태면 isFollowing 포함
     * - 비로그인 상태면 isFollowing = false
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(
            @PathVariable Long userId,
            @AuthenticationPrincipal Long currentUserId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getUserProfile(userId, currentUserId)));
    }

    /**
     * POST /api/users/{userId}/follow
     * 팔로우 (로그인 필수)
     */
    @PostMapping("/{userId}/follow")
    public ResponseEntity<ApiResponse<Void>> follow(
            @PathVariable Long userId,
            @AuthenticationPrincipal Long currentUserId) {
        userService.follow(currentUserId, userId);
        return ResponseEntity.ok(ApiResponse.ok("팔로우 했습니다."));
    }

    /**
     * DELETE /api/users/{userId}/follow
     * 언팔로우 (로그인 필수)
     */
    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<ApiResponse<Void>> unfollow(
            @PathVariable Long userId,
            @AuthenticationPrincipal Long currentUserId) {
        userService.unfollow(currentUserId, userId);
        return ResponseEntity.ok(ApiResponse.ok("언팔로우 했습니다."));
    }

    /**
     * GET /api/users/{userId}/followers
     * 팔로워 목록 조회
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getFollowers(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getFollowers(userId)));
    }

    /**
     * GET /api/users/{userId}/following
     * 팔로잉 목록 조회
     */
    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getFollowing(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getFollowing(userId)));
    }
}
