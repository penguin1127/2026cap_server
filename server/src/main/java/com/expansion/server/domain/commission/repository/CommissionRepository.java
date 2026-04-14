package com.expansion.server.domain.commission.repository;

import com.expansion.server.domain.commission.entity.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    Page<Commission> findByClient_UserId(Long clientId, Pageable pageable);

    Page<Commission> findByArtist_UserId(Long artistId, Pageable pageable);

    Page<Commission> findByStatus(String status, Pageable pageable);
}
