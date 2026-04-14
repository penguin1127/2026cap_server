package com.expansion.server.domain.gallery.repository;

import com.expansion.server.domain.gallery.entity.GalleryPost;
import com.expansion.server.domain.gallery.entity.GalleryType;
import com.expansion.server.domain.gallery.entity.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GalleryPostRepository extends JpaRepository<GalleryPost, Long> {

    // 공개 게시물 목록 (타입별)
    Page<GalleryPost> findByVisibilityAndGalleryType(Visibility visibility, GalleryType galleryType, Pageable pageable);

    // 특정 유저의 게시물 (본인 조회 시 전체, 타인 조회 시 PUBLIC만)
    Page<GalleryPost> findByUser_UserIdAndVisibility(Long userId, Visibility visibility, Pageable pageable);

    Page<GalleryPost> findByUser_UserId(Long userId, Pageable pageable);

    // 카테고리별 공개 게시물
    Page<GalleryPost> findByCategory_CategoryIdAndVisibility(Long categoryId, Visibility visibility, Pageable pageable);

    // 태그로 게시물 검색
    @Query("""
            SELECT DISTINCT p FROM GalleryPost p
            JOIN p.postTags pt
            JOIN pt.tag t
            WHERE t.tagName = :tagName
            AND p.visibility = 'PUBLIC'
            """)
    Page<GalleryPost> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    // 제목/설명 키워드 검색
    @Query("""
            SELECT p FROM GalleryPost p
            WHERE p.visibility = 'PUBLIC'
            AND (p.title LIKE %:keyword% OR p.description LIKE %:keyword%)
            """)
    Page<GalleryPost> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 리믹스 원본 참조 게시물 수
    long countByOriginPost_PostId(Long originPostId);
}
