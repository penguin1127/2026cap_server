package com.expansion.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_blocks")
@Getter
@NoArgsConstructor
public class UserBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long blockId;

    /** 차단을 건 사용자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 'USER' 또는 'TAG' */
    @Column(name = "block_type", nullable = false, length = 20)
    private String blockType;

    /** block_type = 'USER' 일 때 차단 대상 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    /** block_type = 'TAG' 일 때 차단 대상 태그명 */
    @Column(name = "target_tag", length = 100)
    private String targetTag;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserBlock(User user, String blockType, User targetUser, String targetTag) {
        this.user = user;
        this.blockType = blockType;
        this.targetUser = targetUser;
        this.targetTag = targetTag;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
