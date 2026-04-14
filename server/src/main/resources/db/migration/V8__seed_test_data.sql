-- V8: 테스트 시드 데이터
-- 비밀번호: password123 (BCrypt 해시)

-- ── 유저 3명 ──────────────────────────────────────────────
INSERT INTO users (email, password_hash, role, status, email_verified)
VALUES
    ('spriteknight@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHqq', 'USER', 'ACTIVE', TRUE),
    ('pixelwitch@test.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHqq', 'USER', 'ACTIVE', TRUE),
    ('neonbrush@test.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHqq', 'USER', 'ACTIVE', TRUE);

-- ── 프로필 3개 ────────────────────────────────────────────
INSERT INTO profiles (user_id, nickname, bio, is_public)
SELECT user_id, 'SpriteKnight', '판타지 RPG 캐릭터 전문 픽셀아티스트', TRUE
FROM users WHERE email = 'spriteknight@test.com';

INSERT INTO profiles (user_id, nickname, bio, is_public)
SELECT user_id, 'PixelWitch', '다크 판타지 및 사이버펑크 배경 전문', TRUE
FROM users WHERE email = 'pixelwitch@test.com';

INSERT INTO profiles (user_id, nickname, bio, is_public)
SELECT user_id, 'NeonBrush', '애니메이션 스프라이트 및 이펙트 전문', TRUE
FROM users WHERE email = 'neonbrush@test.com';

-- ── 카테고리 ──────────────────────────────────────────────
INSERT INTO categories (name, type, sort_order)
VALUES
    ('캐릭터',  'GALLERY', 1),
    ('배경',    'GALLERY', 2),
    ('에셋',    'ASSET',   1),
    ('타일셋',  'ASSET',   2);

-- ── 태그 ──────────────────────────────────────────────────
INSERT INTO tags (tag_name)
VALUES
    ('판타지'), ('RPG'), ('캐릭터'), ('배경'), ('사이버펑크'),
    ('애니메이션'), ('귀여운'), ('레트로'), ('픽셀아트'), ('무료');

-- ── 갤러리 게시물 3개 ─────────────────────────────────────
INSERT INTO gallery_posts (user_id, title, description, gallery_type, visibility, view_count, like_count, comment_count)
SELECT user_id, '판타지 기사 스프라이트', '16x16 판타지 기사 캐릭터 스프라이트입니다. Walk/Attack/Idle 애니메이션 포함.', 'FREE', 'PUBLIC', 142, 38, 5
FROM users WHERE email = 'spriteknight@test.com';

INSERT INTO gallery_posts (user_id, title, description, gallery_type, visibility, view_count, like_count, comment_count)
SELECT user_id, '사이버펑크 시티 배경', '야경 사이버펑크 도시 배경 픽셀아트입니다. 64x64 해상도.', 'FREE', 'PUBLIC', 89, 21, 3
FROM users WHERE email = 'pixelwitch@test.com';

INSERT INTO gallery_posts (user_id, title, description, gallery_type, visibility, view_count, like_count, comment_count)
SELECT user_id, '폭발 이펙트 애니메이션', '8프레임 폭발 이펙트 스프라이트시트. 32x32 해상도.', 'DEDICATED', 'PUBLIC', 67, 15, 2
FROM users WHERE email = 'neonbrush@test.com';

-- ── 에셋 3개 ──────────────────────────────────────────────
INSERT INTO assets (user_id, title, description, price, is_free, download_count, like_count, status)
SELECT user_id, '판타지 캐릭터 스프라이트 팩', '10종 판타지 캐릭터 16x16 스프라이트 팩. 상업적 이용 가능.', 0, TRUE, 234, 47, 'ACTIVE'
FROM users WHERE email = 'spriteknight@test.com';

INSERT INTO assets (user_id, title, description, price, is_free, download_count, like_count, status)
SELECT user_id, '사이버펑크 타일셋', '사이버펑크 도시 배경용 32x32 타일셋. 60종 타일 포함.', 5000, FALSE, 88, 29, 'ACTIVE'
FROM users WHERE email = 'pixelwitch@test.com';

INSERT INTO assets (user_id, title, description, price, is_free, download_count, like_count, status)
SELECT user_id, '이펙트 스프라이트시트 모음', '불꽃/폭발/마법 이펙트 스프라이트시트 5종 세트.', 3000, FALSE, 56, 18, 'ACTIVE'
FROM users WHERE email = 'neonbrush@test.com';

-- ── 의뢰 게시글 3개 (request_posts) ──────────────────────
INSERT INTO request_posts (client_id, title, description, budget_min, budget_max, deadline, status)
SELECT user_id, 'RPG 주인공 캐릭터 스프라이트 의뢰', '판타지 RPG 주인공 캐릭터 16x16 스프라이트 제작 의뢰입니다. Walk/Run/Attack/Idle 4방향 애니메이션 필요합니다.', 50000, 100000, CURRENT_DATE + INTERVAL '30 days', 'OPEN'
FROM users WHERE email = 'spriteknight@test.com';

INSERT INTO request_posts (client_id, title, description, budget_min, budget_max, deadline, status)
SELECT user_id, '인디게임 배경 타일셋 제작 의뢰', '탑뷰 2D 인디게임용 32x32 타일셋 제작 의뢰. 초원/숲/마을 3종 필요합니다.', 100000, 200000, CURRENT_DATE + INTERVAL '45 days', 'OPEN'
FROM users WHERE email = 'pixelwitch@test.com';

INSERT INTO request_posts (client_id, title, description, budget_min, budget_max, deadline, status)
SELECT user_id, '모바일 게임 UI 아이콘 세트 의뢰', '모바일 게임용 픽셀 아이콘 20종 제작. 24x24 해상도, PNG 납품.', 40000, 80000, CURRENT_DATE + INTERVAL '20 days', 'OPEN'
FROM users WHERE email = 'neonbrush@test.com';
