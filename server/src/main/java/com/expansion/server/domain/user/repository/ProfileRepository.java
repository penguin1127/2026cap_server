package com.expansion.server.domain.user.repository;

import com.expansion.server.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser_UserId(Long userId);

    boolean existsByNickname(String nickname);

    Optional<Profile> findByNickname(String nickname);

    List<Profile> findAllByUser_UserIdIn(Collection<Long> userIds);
}
