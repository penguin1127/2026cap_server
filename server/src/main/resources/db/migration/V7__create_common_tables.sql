-- V7: 공통 기능 테이블 생성 (likes, favorites, notifications, commission_rates, settlements)

CREATE TABLE likes (
    like_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT      NOT NULL REFERENCES users(user_id),
    target_id   BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, target_id, target_type)
);

CREATE TABLE favorites (
    favorite_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT      NOT NULL REFERENCES users(user_id),
    target_id   BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, target_id, target_type)
);

CREATE TABLE notifications (
    notification_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         BIGINT      NOT NULL REFERENCES users(user_id),
    sender_id       BIGINT      REFERENCES users(user_id),
    type            VARCHAR(50) NOT NULL,
    title           VARCHAR(100) NOT NULL,
    message         TEXT,
    target_id       BIGINT,
    target_type     VARCHAR(20),
    is_read         BOOLEAN     NOT NULL DEFAULT FALSE,
    read_at         TIMESTAMP,
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE commission_rates (
    rate_id     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    policy_name VARCHAR(50)   NOT NULL,
    fee_percent DECIMAL(5,2)  NOT NULL,
    is_active   BOOLEAN       NOT NULL DEFAULT TRUE,
    start_date  DATE          NOT NULL,
    end_date    DATE
);

CREATE TABLE settlements (
    settlement_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT         NOT NULL REFERENCES users(user_id),
    source_type      VARCHAR(20)    NOT NULL,
    target_month     DATE           NOT NULL,
    total_amount     DECIMAL(12,2)  NOT NULL,
    total_fee        DECIMAL(12,2)  NOT NULL,
    payout_amount    DECIMAL(12,2)  NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'WAITING',
    settlement_notes TEXT,
    paid_at          TIMESTAMP,
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW()
);
