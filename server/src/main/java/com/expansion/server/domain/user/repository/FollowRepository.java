package com.expansion.server.domain.user.repository;

import com.expansion.server.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);

    boolean existsByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);
}
