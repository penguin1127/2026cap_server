-- V13: user_blocks UNIQUE 제약 조건 수정
-- 기존 UNIQUE NULLS NOT DISTINCT 제약은 NULL 열을 동일하게 취급하므로
-- 사용자당 USER 차단 1건, TAG 차단 1건만 허용되는 버그 존재 → 부분 인덱스로 교체

ALTER TABLE user_blocks DROP CONSTRAINT IF EXISTS uq_user_block_user;
ALTER TABLE user_blocks DROP CONSTRAINT IF EXISTS uq_user_block_tag;

-- USER 차단: 같은 사용자가 동일 대상 유저를 중복 차단하지 못하도록
CREATE UNIQUE INDEX uq_user_block_user
    ON user_blocks(user_id, target_user_id)
    WHERE block_type = 'USER';

-- TAG 차단: 같은 사용자가 동일 태그를 중복 차단하지 못하도록
CREATE UNIQUE INDEX uq_user_block_tag
    ON user_blocks(user_id, target_tag)
    WHERE block_type = 'TAG';
