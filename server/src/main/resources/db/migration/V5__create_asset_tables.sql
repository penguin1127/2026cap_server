-- V5: 에셋 스토어 도메인 테이블 생성

CREATE TABLE asset_license_types (
    license_id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                VARCHAR(50) NOT NULL,
    can_commercial      BOOLEAN     NOT NULL DEFAULT FALSE,
    can_modify          BOOLEAN     NOT NULL DEFAULT FALSE,
    can_redistribute    BOOLEAN     NOT NULL DEFAULT FALSE,
    require_attribution BOOLEAN     NOT NULL DEFAULT TRUE,
    is_exclusive        BOOLEAN     NOT NULL DEFAULT FALSE,
    license_url         VARCHAR(500),
    description         TEXT
);

CREATE TABLE payments (
    payment_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT         NOT NULL REFERENCES users(user_id),
    payment_key      VARCHAR(255),
    amount           DECIMAL(10,2)  NOT NULL,
    tax_amount       DECIMAL(10,2),
    total_commission DECIMAL(10,2)  NOT NULL DEFAULT 0,
    method           VARCHAR(50)    NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE assets (
    asset_id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT         NOT NULL REFERENCES users(user_id),
    category_id      BIGINT         REFERENCES categories(category_id),
    license_type_id  BIGINT         REFERENCES asset_license_types(license_id),
    title            VARCHAR(100)   NOT NULL,
    description      TEXT,
    price            DECIMAL(10,2)  NOT NULL DEFAULT 0,
    is_free          BOOLEAN        NOT NULL DEFAULT TRUE,
    asset_type       VARCHAR(20)    NOT NULL,
    thumbnail_url    VARCHAR(500)   NOT NULL,
    preview_url      VARCHAR(500),
    file_format      VARCHAR(20)    NOT NULL,
    is_animated      BOOLEAN        NOT NULL DEFAULT FALSE,
    resolution       VARCHAR(50)    NOT NULL,
    file_size        BIGINT         NOT NULL,
    download_count   INT            NOT NULL DEFAULT 0,
    view_count       INT            NOT NULL DEFAULT 0,
    like_count       INT            NOT NULL DEFAULT 0,
    average_rating   DECIMAL(3,2)   NOT NULL DEFAULT 0,
    review_count     INT            NOT NULL DEFAULT 0,
    is_featured      BOOLEAN        NOT NULL DEFAULT FALSE,
    palette_data     JSONB,
    ai_tags          JSONB,
    min_version      VARCHAR(20),
    status           VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE asset_images (
    image_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id   BIGINT       NOT NULL REFERENCES assets(asset_id),
    image_url  VARCHAR(500) NOT NULL,
    sort_order INT          NOT NULL DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE asset_versions (
    version_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id       BIGINT       NOT NULL REFERENCES assets(asset_id),
    version_number INT          NOT NULL,
    version_name   VARCHAR(50)  NOT NULL,
    file_url       VARCHAR(500) NOT NULL,
    file_size      BIGINT       NOT NULL,
    change_note    TEXT,
    is_current     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    UNIQUE (asset_id, version_number),
    UNIQUE (asset_id, version_name)
);

CREATE TABLE asset_purchases (
    purchase_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT        NOT NULL REFERENCES users(user_id),
    asset_id    BIGINT        NOT NULL REFERENCES assets(asset_id),
    payment_id  BIGINT        REFERENCES payments(payment_id),
    price_paid  DECIMAL(10,2) NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, asset_id)
);

CREATE TABLE asset_sales_stats (
    stat_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id       BIGINT        NOT NULL REFERENCES assets(asset_id),
    date           DATE          NOT NULL,
    daily_sales    INT           NOT NULL DEFAULT 0,
    daily_revenue  DECIMAL(10,2) NOT NULL DEFAULT 0,
    net_revenue    DECIMAL(10,2) NOT NULL DEFAULT 0,
    daily_views    INT           NOT NULL DEFAULT 0,
    daily_likes    INT           NOT NULL DEFAULT 0,
    daily_downloads INT          NOT NULL DEFAULT 0,
    created_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
    UNIQUE (asset_id, date)
);

CREATE TABLE asset_comments (
    comment_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id          BIGINT    NOT NULL REFERENCES assets(asset_id),
    user_id           BIGINT    NOT NULL REFERENCES users(user_id),
    purchase_id       BIGINT    REFERENCES asset_purchases(purchase_id),
    parent_comment_id BIGINT    REFERENCES asset_comments(comment_id),
    rating            SMALLINT  CHECK (rating BETWEEN 1 AND 5),
    content           TEXT      NOT NULL,
    is_author         BOOLEAN   NOT NULL DEFAULT FALSE,
    is_deleted        BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE asset_tags (
    asset_tag_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id     BIGINT    NOT NULL REFERENCES assets(asset_id),
    tag_id       BIGINT    NOT NULL REFERENCES tags(tag_id),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (asset_id, tag_id)
);

CREATE TABLE asset_reports (
    report_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id      BIGINT      NOT NULL REFERENCES assets(asset_id),
    reporter_id   BIGINT      NOT NULL REFERENCES users(user_id),
    admin_id      BIGINT      REFERENCES users(user_id),
    report_type   VARCHAR(50) NOT NULL,
    reason        TEXT        NOT NULL,
    admin_comment TEXT,
    status        VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    resolved_at   TIMESTAMP,
    created_at    TIMESTAMP   NOT NULL DEFAULT NOW()
);
