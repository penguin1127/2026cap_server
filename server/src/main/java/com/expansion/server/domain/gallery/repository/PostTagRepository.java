package com.expansion.server.domain.gallery.repository;

import com.expansion.server.domain.gallery.entity.PostTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByPost_PostId(Long postId);

    /** post와 tag를 한 번에 fetch — N+1 방지 */
    @EntityGraph(attributePaths = {"post", "tag"})
    List<PostTag> findByPost_PostIdIn(Collection<Long> postIds);

    void deleteByPost_PostId(Long postId);
}
