-- V2: 공통 기준 테이블 생성 (categories, tags)
-- gallery_posts / assets 양쪽에서 FK 참조하므로 먼저 생성

CREATE TABLE categories (
    category_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    parent_id   BIGINT      REFERENCES categories(category_id),
    name        VARCHAR(50) NOT NULL UNIQUE,
    type        VARCHAR(20) NOT NULL,
    sort_order  INT,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE tags (
    tag_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tag_name   VARCHAR(50) NOT NULL UNIQUE,
    post_count INT         NOT NULL DEFAULT 0,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW()
);
