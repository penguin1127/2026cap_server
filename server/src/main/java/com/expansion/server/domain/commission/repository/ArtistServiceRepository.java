package com.expansion.server.domain.commission.repository;

import com.expansion.server.domain.commission.entity.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistServiceRepository extends JpaRepository<ArtistService, Long> {

    Page<ArtistService> findByStatus(String status, Pageable pageable);

    Page<ArtistService> findByArtist_UserId(Long artistId, Pageable pageable);
}
