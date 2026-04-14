package com.expansion.server.domain.commission.repository;

import com.expansion.server.domain.commission.entity.RequestPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestPostRepository extends JpaRepository<RequestPost, Long> {

    Page<RequestPost> findByStatus(String status, Pageable pageable);

    Page<RequestPost> findByClient_UserId(Long clientId, Pageable pageable);
}
