-- V3: 에디터/프로젝트 도메인 테이블 생성

CREATE TABLE projects (
    project_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT       NOT NULL REFERENCES users(user_id),
    title            VARCHAR(100) NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    width            INT          NOT NULL,
    height           INT          NOT NULL,
    background_color VARCHAR(10),
    file_url         VARCHAR(500),
    thumbnail_url    VARCHAR(500),
    last_accessed_at TIMESTAMP,
    is_public        BOOLEAN      NOT NULL DEFAULT FALSE,
    ai_analyzed      BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE layers (
    layer_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id  BIGINT      NOT NULL REFERENCES projects(project_id),
    name        VARCHAR(50) NOT NULL,
    layer_order INT         NOT NULL,
    blend_mode  VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    is_locked   BOOLEAN     NOT NULL DEFAULT FALSE,
    is_visible  BOOLEAN     NOT NULL DEFAULT TRUE,
    opacity     FLOAT       NOT NULL DEFAULT 1.0,
    file_url    VARCHAR(500),
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE project_members (
    member_id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id BIGINT      NOT NULL REFERENCES projects(project_id),
    user_id    BIGINT      NOT NULL REFERENCES users(user_id),
    inviter_id BIGINT      REFERENCES users(user_id),
    permission VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (project_id, user_id)
);
