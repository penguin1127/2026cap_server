-- V1: 사용자 도메인 테이블 생성

CREATE TABLE users (
    user_id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email            VARCHAR(100)  NOT NULL UNIQUE,
    password_hash    VARCHAR(255),
    role             VARCHAR(20)   NOT NULL DEFAULT 'USER',
    status           VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    email_verified   BOOLEAN       NOT NULL DEFAULT FALSE,
    social_id        VARCHAR(255),
    social_provider  VARCHAR(20),
    last_login_at    TIMESTAMP,
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    deleted_at       TIMESTAMP
);

CREATE TABLE profiles (
    profile_id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id            BIGINT        NOT NULL UNIQUE REFERENCES users(user_id),
    nickname           VARCHAR(50)   NOT NULL UNIQUE,
    bio                VARCHAR(255),
    website_url        VARCHAR(255),
    profile_image_url  VARCHAR(500),
    follower_count     INT           NOT NULL DEFAULT 0,
    following_count    INT           NOT NULL DEFAULT 0,
    is_public          BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at         TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE follows (
    follow_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    follower_id  BIGINT    NOT NULL REFERENCES users(user_id),
    following_id BIGINT    NOT NULL REFERENCES users(user_id),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (follower_id, following_id)
);

CREATE TABLE refresh_tokens (
    token_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users(user_id),
    token_hash  VARCHAR(255) NOT NULL UNIQUE,
    user_agent  VARCHAR(255),
    ip_address  VARCHAR(45),
    expires_at  TIMESTAMP    NOT NULL,
    revoked_at  TIMESTAMP,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
