-- V6: 커미션 도메인 테이블 생성 (v2 재설계)

-- ──────────────────────────────────────────────
-- 작가 서비스 등록형
-- ──────────────────────────────────────────────

CREATE TABLE commission_services (
    service_id     BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    artist_id      BIGINT        NOT NULL REFERENCES users(user_id),
    title          VARCHAR(100)  NOT NULL,
    description    TEXT,
    service_type   VARCHAR(20)   NOT NULL,  -- OPTION(가격 고정) / QUOTE(가격 협의)
    base_price     DECIMAL(10,2),           -- OPTION형만 사용
    price_min      DECIMAL(10,2),           -- QUOTE형만 사용
    price_max      DECIMAL(10,2),           -- QUOTE형만 사용
    estimated_days INT,
    status         VARCHAR(20)   NOT NULL DEFAULT 'OPEN',  -- OPEN / CLOSED
    created_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_service_options (
    option_id   BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    service_id  BIGINT        NOT NULL REFERENCES commission_services(service_id),
    name        VARCHAR(100)  NOT NULL,
    description TEXT,
    extra_price DECIMAL(10,2) NOT NULL DEFAULT 0,
    sort_order  INT           NOT NULL DEFAULT 0
);

CREATE TABLE commission_service_images (
    image_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    service_id BIGINT       NOT NULL REFERENCES commission_services(service_id),
    image_url  VARCHAR(500) NOT NULL,
    sort_order INT          NOT NULL DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ──────────────────────────────────────────────
-- 의뢰자 게시판형
-- ──────────────────────────────────────────────

CREATE TABLE request_posts (
    request_post_id BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    client_id       BIGINT        NOT NULL REFERENCES users(user_id),
    title           VARCHAR(100)  NOT NULL,
    description     TEXT,
    budget_min      DECIMAL(10,2),
    budget_max      DECIMAL(10,2),
    deadline        DATE,
    status          VARCHAR(20)   NOT NULL DEFAULT 'OPEN',  -- OPEN / CLOSED
    created_at      TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE request_post_images (
    image_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    request_post_id BIGINT       NOT NULL REFERENCES request_posts(request_post_id),
    image_url       VARCHAR(500) NOT NULL,
    sort_order      INT          NOT NULL DEFAULT 0,
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_applications (
    application_id  BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    request_post_id BIGINT        NOT NULL REFERENCES request_posts(request_post_id),
    artist_id       BIGINT        NOT NULL REFERENCES users(user_id),
    message         TEXT,
    proposed_price  DECIMAL(10,2),
    status          VARCHAR(20)   NOT NULL DEFAULT 'PENDING',  -- PENDING / ACCEPTED / REJECTED
    created_at      TIMESTAMP     NOT NULL DEFAULT NOW(),
    UNIQUE (request_post_id, artist_id)
);

-- ──────────────────────────────────────────────
-- 계약 성사 레코드 (의뢰자가 작가를 선택한 시점에 생성)
-- ──────────────────────────────────────────────

CREATE TABLE commissions (
    commission_id   BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commission_type VARCHAR(20)   NOT NULL,  -- SERVICE_OPTION / SERVICE_QUOTE / REQUEST
    client_id       BIGINT        NOT NULL REFERENCES users(user_id),
    artist_id       BIGINT        NOT NULL REFERENCES users(user_id),
    service_id      BIGINT        REFERENCES commission_services(service_id),
    request_post_id BIGINT        REFERENCES request_posts(request_post_id),
    application_id  BIGINT        REFERENCES commission_applications(application_id),
    payment_id      BIGINT        REFERENCES payments(payment_id),
    agreed_price    DECIMAL(10,2) NOT NULL,
    agreed_deadline DATE,
    status          VARCHAR(20)   NOT NULL DEFAULT 'IN_PROGRESS',
    -- IN_PROGRESS / REVIEW / COMPLETED / CANCELLED
    file_url        VARCHAR(500),
    completed_at    TIMESTAMP,
    created_at      TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_files (
    file_id       BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commission_id BIGINT       NOT NULL REFERENCES commissions(commission_id),
    uploader_id   BIGINT       NOT NULL REFERENCES users(user_id),
    file_type     VARCHAR(20),  -- DRAFT / FINAL
    file_url      VARCHAR(500) NOT NULL,
    file_name     VARCHAR(200) NOT NULL,
    file_size     BIGINT,
    is_public     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ──────────────────────────────────────────────
-- 채팅 (커미션별 1:1, 계약 성사 시 자동 생성)
-- ──────────────────────────────────────────────

CREATE TABLE chat_rooms (
    room_id       BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commission_id BIGINT    NOT NULL UNIQUE REFERENCES commissions(commission_id),
    created_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE chat_messages (
    message_id BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id    BIGINT    NOT NULL REFERENCES chat_rooms(room_id),
    sender_id  BIGINT    NOT NULL REFERENCES users(user_id),
    content    TEXT      NOT NULL,
    is_read    BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
