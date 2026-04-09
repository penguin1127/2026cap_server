-- V6: 커미션 도메인 테이블 생성

CREATE TABLE commissions (
    commission_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    client_id     BIGINT        NOT NULL REFERENCES users(user_id),
    artist_id     BIGINT        NOT NULL REFERENCES users(user_id),
    payment_id    BIGINT        REFERENCES payments(payment_id),
    title         VARCHAR(100)  NOT NULL,
    description   TEXT,
    price         DECIMAL(10,2) NOT NULL,
    status        VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    file_url      VARCHAR(500),
    thumbnail_url VARCHAR(500),
    deadline      DATE,
    completed_at  TIMESTAMP,
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_files (
    file_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commission_id BIGINT       NOT NULL REFERENCES commissions(commission_id),
    file_type     VARCHAR(20),
    file_size     BIGINT,
    file_url      VARCHAR(500) NOT NULL,
    file_name     VARCHAR(100) NOT NULL,
    is_public     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_images (
    image_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commission_id BIGINT       NOT NULL REFERENCES commissions(commission_id),
    image_url     VARCHAR(500) NOT NULL,
    image_order   INT          NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);
