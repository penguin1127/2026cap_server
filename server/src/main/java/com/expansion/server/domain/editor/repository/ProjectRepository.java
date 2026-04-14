package com.expansion.server.domain.editor.repository;

import com.expansion.server.domain.editor.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByUser_UserIdAndStatus(Long userId, String status, Pageable pageable);

    Optional<Project> findByProjectIdAndUser_UserId(Long projectId, Long userId);
}
