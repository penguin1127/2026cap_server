package com.expansion.server.domain.user.repository;

import com.expansion.server.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);

    boolean existsByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);

    /** 나를 팔로우하는 사람들 (팔로워 목록) */
    List<Follow> findAllByFollowing_UserId(Long followingId);

    /** 내가 팔로우하는 사람들 (팔로잉 목록) */
    List<Follow> findAllByFollower_UserId(Long followerId);
}
