package com.expansion.server.domain.common.repository;

import com.expansion.server.domain.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    boolean existsByTagName(String tagName);

    List<Tag> findTop20ByOrderByPostCountDesc();
}
