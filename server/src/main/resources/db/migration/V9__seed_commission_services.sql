-- V9: 작가 서비스 테스트 데이터 추가

-- ── SpriteKnight 서비스 2개 ───────────────────────────────────
INSERT INTO commission_services (artist_id, title, description, service_type, base_price, estimated_days, status)
SELECT user_id,
       '판타지 RPG 캐릭터 스프라이트 제작',
       '16×16 ~ 64×64 해상도 판타지 RPG 캐릭터 스프라이트를 제작합니다. Walk/Run/Attack/Idle 4방향 애니메이션 포함. PNG 프레임 + 스프라이트시트로 납품합니다. 상업적 이용 가능 라이선스 포함.',
       'OPTION',
       35000,
       14,
       'OPEN'
FROM users WHERE email = 'spriteknight@test.com';

INSERT INTO commission_services (artist_id, title, description, service_type, price_min, price_max, estimated_days, status)
SELECT user_id,
       '게임 타일셋 커스텀 제작',
       '인디게임 또는 개인 프로젝트용 커스텀 타일셋 제작. 해상도/종류/수량에 따라 가격 협의. 초원, 던전, 마을 등 다양한 테마 가능합니다.',
       'QUOTE',
       80000,
       300000,
       21,
       'OPEN'
FROM users WHERE email = 'spriteknight@test.com';

-- ── PixelWitch 서비스 2개 ─────────────────────────────────────
INSERT INTO commission_services (artist_id, title, description, service_type, base_price, estimated_days, status)
SELECT user_id,
       '사이버펑크 / 다크 판타지 배경 일러스트',
       '야경 도시, 던전, 지하 연구소 등 어두운 분위기의 배경 픽셀아트를 전문으로 합니다. 64×64 이상 해상도. 레이어 파일(Aseprite) 포함 납품.',
       'OPTION',
       55000,
       10,
       'OPEN'
FROM users WHERE email = 'pixelwitch@test.com';

INSERT INTO commission_services (artist_id, title, description, service_type, price_min, price_max, estimated_days, status)
SELECT user_id,
       'UI / 아이콘 픽셀 디자인',
       '게임 UI, HUD, 인벤토리 아이콘 등 픽셀 UI 요소 디자인. 수량 및 복잡도에 따라 가격 협의. 포토샵 / Aseprite 파일 원본 납품 가능.',
       'QUOTE',
       20000,
       150000,
       7,
       'OPEN'
FROM users WHERE email = 'pixelwitch@test.com';

-- ── NeonBrush 서비스 1개 ──────────────────────────────────────
INSERT INTO commission_services (artist_id, title, description, service_type, base_price, estimated_days, status)
SELECT user_id,
       '애니메이션 이펙트 스프라이트 제작',
       '불꽃, 폭발, 마법, 물 등 다양한 이펙트 애니메이션 스프라이트시트 제작. 최소 8프레임. 32×32 해상도 기준. Unity / Godot 연동 설정 파일 포함 납품 가능.',
       'OPTION',
       45000,
       7,
       'OPEN'
FROM users WHERE email = 'neonbrush@test.com';
