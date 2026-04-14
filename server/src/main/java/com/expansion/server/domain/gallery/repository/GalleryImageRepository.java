package com.expansion.server.domain.gallery.repository;

import com.expansion.server.domain.gallery.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {

    List<GalleryImage> findByPost_PostIdOrderBySortOrderAsc(Long postId);

    void deleteByPost_PostId(Long postId);
}
