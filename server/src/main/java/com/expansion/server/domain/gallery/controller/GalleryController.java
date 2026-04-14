package com.expansion.server.domain.gallery.controller;

import com.expansion.server.domain.gallery.dto.*;
import com.expansion.server.domain.gallery.service.GalleryService;
import com.expansion.server.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    // ──────────────────────────────────────────────
    // 게시물 목록 조회 (비로그인 허용)
    // GET /api/gallery?type=FREE&page=0&size=20&sort=createdAt,desc
    // ──────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<ApiResponse<Page<GalleryPostSummary>>> getPostList(
            @RequestParam(defaultValue = "FREE") String type,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success(galleryService.getPostList(type, pageable)));
    }

    // ──────────────────────────────────────────────
    // 게시물 상세 조회 (비로그인 허용)
    // GET /api/gallery/{postId}
    // ──────────────────────────────────────────────

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<GalleryPostResponse>> getPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long currentUserId) {

        return ResponseEntity.ok(ApiResponse.success(
                galleryService.getPostAndIncrementView(postId, currentUserId)));
    }

    // ──────────────────────────────────────────────
    // 게시물 등록 (로그인 필수)
    // POST /api/gallery
    // ──────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<ApiResponse<GalleryPostResponse>> createPost(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody GalleryPostCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(galleryService.createPost(userId, request)));
    }

    // ──────────────────────────────────────────────
    // 게시물 수정 (로그인 + 작성자)
    // PATCH /api/gallery/{postId}
    // ──────────────────────────────────────────────

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<GalleryPostResponse>> updatePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody GalleryPostUpdateRequest request) {

        return ResponseEntity.ok(ApiResponse.success(
                galleryService.updatePost(userId, postId, request)));
    }

    // ──────────────────────────────────────────────
    // 게시물 삭제 (로그인 + 작성자)
    // DELETE /api/gallery/{postId}
    // ──────────────────────────────────────────────

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId) {

        galleryService.deletePost(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // ──────────────────────────────────────────────
    // 좋아요 토글 (로그인 필수)
    // POST /api/gallery/{postId}/like
    // ──────────────────────────────────────────────

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Boolean>> toggleLike(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId) {

        boolean liked = galleryService.toggleLike(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(liked));
    }

    // ──────────────────────────────────────────────
    // 댓글 목록 조회 (비로그인 허용)
    // GET /api/gallery/{postId}/comments
    // ──────────────────────────────────────────────

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<Page<GalleryCommentResponse>>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success(
                galleryService.getComments(postId, pageable)));
    }

    // ──────────────────────────────────────────────
    // 댓글 작성 (로그인 필수)
    // POST /api/gallery/{postId}/comments
    // ──────────────────────────────────────────────

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<GalleryCommentResponse>> createComment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody GalleryCommentCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        galleryService.createComment(userId, postId, request)));
    }

    // ──────────────────────────────────────────────
    // 댓글 삭제 (로그인 + 작성자)
    // DELETE /api/gallery/{postId}/comments/{commentId}
    // ──────────────────────────────────────────────

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId) {

        galleryService.deleteComment(userId, commentId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // ──────────────────────────────────────────────
    // 검색 (비로그인 허용)
    // GET /api/gallery/search?keyword=xxx
    // ──────────────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<GalleryPostSummary>>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success(
                galleryService.searchPosts(keyword, pageable)));
    }

    // ──────────────────────────────────────────────
    // 태그별 조회 (비로그인 허용)
    // GET /api/gallery/tags/{tagName}
    // ──────────────────────────────────────────────

    @GetMapping("/tags/{tagName}")
    public ResponseEntity<ApiResponse<Page<GalleryPostSummary>>> getByTag(
            @PathVariable String tagName,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success(
                galleryService.getPostsByTag(tagName, pageable)));
    }
}
