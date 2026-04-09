-- V4: 갤러리 도메인 테이블 생성

CREATE TABLE gallery_posts (
    post_id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT       NOT NULL REFERENCES users(user_id),
    project_id       BIGINT       REFERENCES projects(project_id),
    origin_post_id   BIGINT       REFERENCES gallery_posts(post_id),
    category_id      BIGINT       REFERENCES categories(category_id),
    title            VARCHAR(100) NOT NULL,
    description      TEXT,
    gallery_type     VARCHAR(20)  NOT NULL,
    visibility       VARCHAR(20)  NOT NULL DEFAULT 'PUBLIC',
    is_editable      BOOLEAN      NOT NULL DEFAULT FALSE,
    is_collaborative BOOLEAN      NOT NULL DEFAULT FALSE,
    remix_count      INT          NOT NULL DEFAULT 0,
    view_count       INT          NOT NULL DEFAULT 0,
    like_count       INT          NOT NULL DEFAULT 0,
    palette_data     JSONB,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    deleted_at       TIMESTAMP
);

CREATE TABLE post_collaborators (
    collaborator_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id         BIGINT      NOT NULL REFERENCES gallery_posts(post_id),
    user_id         BIGINT      NOT NULL REFERENCES users(user_id),
    role            VARCHAR(50),
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (post_id, user_id)
);

CREATE TABLE gallery_images (
    image_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id      BIGINT       NOT NULL REFERENCES gallery_posts(post_id),
    image_url    VARCHAR(500) NOT NULL,
    file_name    VARCHAR(255),
    file_size    INT,
    width        INT,
    height       INT,
    mime_type    VARCHAR(50),
    "order"      INT          NOT NULL DEFAULT 0,
    is_thumbnail BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE gallery_comments (
    comment_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id           BIGINT    NOT NULL REFERENCES gallery_posts(post_id),
    user_id           BIGINT    NOT NULL REFERENCES users(user_id),
    parent_comment_id BIGINT    REFERENCES gallery_comments(comment_id),
    depth             INT       NOT NULL DEFAULT 0,
    content           TEXT      NOT NULL,
    like_count        INT       NOT NULL DEFAULT 0,
    is_deleted        BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE post_tags (
    post_tag_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id     BIGINT    NOT NULL REFERENCES gallery_posts(post_id),
    tag_id      BIGINT    NOT NULL REFERENCES tags(tag_id),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (post_id, tag_id)
);

CREATE TABLE gallery_reports (
    report_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    post_id       BIGINT      NOT NULL REFERENCES gallery_posts(post_id),
    reporter_id   BIGINT      NOT NULL REFERENCES users(user_id),
    admin_id      BIGINT      REFERENCES users(user_id),
    report_type   VARCHAR(50) NOT NULL,
    reason        TEXT        NOT NULL,
    admin_comment TEXT,
    status        VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    resolved_at   TIMESTAMP,
    created_at    TIMESTAMP   NOT NULL DEFAULT NOW()
);
