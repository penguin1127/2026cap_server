-- V12: 사용자 차단 테이블 생성

CREATE TABLE user_blocks (
    block_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id        BIGINT       NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    block_type     VARCHAR(20)  NOT NULL,            -- 'USER' | 'TAG'
    target_user_id BIGINT       REFERENCES users(user_id) ON DELETE CASCADE,
    target_tag     VARCHAR(100),
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),

    -- 사용자 차단 중복 방지
    CONSTRAINT uq_user_block_user
        UNIQUE NULLS NOT DISTINCT (user_id, block_type, target_user_id),

    -- 태그 차단 중복 방지
    CONSTRAINT uq_user_block_tag
        UNIQUE NULLS NOT DISTINCT (user_id, block_type, target_tag),

    -- 타입에 따라 반드시 하나만 존재
    CONSTRAINT chk_user_block_target CHECK (
        (block_type = 'USER'  AND target_user_id IS NOT NULL AND target_tag IS NULL) OR
        (block_type = 'TAG'   AND target_tag     IS NOT NULL AND target_user_id IS NULL)
    )
);

CREATE INDEX idx_user_blocks_user_id ON user_blocks(user_id);
