package com.expansion.server.domain.commission.repository;

import com.expansion.server.domain.commission.entity.CommissionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommissionApplicationRepository extends JpaRepository<CommissionApplication, Long> {

    // 특정 의뢰의 지원 목록
    Page<CommissionApplication> findByRequestPost_RequestPostId(Long requestPostId, Pageable pageable);

    // 작가의 내 지원 목록
    Page<CommissionApplication> findByArtist_UserId(Long artistId, Pageable pageable);

    // 중복 지원 체크
    boolean existsByRequestPost_RequestPostIdAndArtist_UserId(Long requestPostId, Long artistId);

    // 특정 지원 조회 (requestPostId + artistId)
    Optional<CommissionApplication> findByRequestPost_RequestPostIdAndArtist_UserId(Long requestPostId, Long artistId);

    // 수락 시 나머지 지원 REJECTED 처리용
    List<CommissionApplication> findByRequestPost_RequestPostIdAndStatusAndApplicationIdNot(
            Long requestPostId, String status, Long acceptedApplicationId);
}
