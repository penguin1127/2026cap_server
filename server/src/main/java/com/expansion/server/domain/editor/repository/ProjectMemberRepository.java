package com.expansion.server.domain.editor.repository;

import com.expansion.server.domain.editor.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByProject_ProjectIdAndUser_UserId(Long projectId, Long userId);

    List<ProjectMember> findByProject_ProjectId(Long projectId);

    Optional<ProjectMember> findByProject_ProjectIdAndUser_UserId(Long projectId, Long userId);
}
