-- Post create DDL
-- 목적:
-- 1. AuthorDao.findById(Integer authorId)에서 authors 테이블의 작성자 정보를 조회한다.
-- 2. PostDao.insert(Post post)에서 posts 테이블에 게시물을 저장한다.
-- 3. Post.mediaList 값을 같은 insert 흐름에서 post_media 테이블에 저장한다.
-- 4. posts.post_id는 seq_posts로 생성하고, post_media.post_id는 생성된 posts.post_id를 참조한다.

CREATE SEQUENCE seq_posts
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_post_media
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE authors (
    author_id NUMBER(10) NOT NULL,
    username VARCHAR2(50 CHAR) NOT NULL,
    display_name VARCHAR2(80 CHAR) NOT NULL,
    profile_image_url VARCHAR2(1000 CHAR),
    has_active_story NUMBER(1) DEFAULT 0 NOT NULL,
    is_viewer NUMBER(1) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT pk_authors PRIMARY KEY (author_id),
    CONSTRAINT uq_authors_username UNIQUE (username),
    CONSTRAINT ck_authors_has_active_story CHECK (has_active_story IN (0, 1)),
    CONSTRAINT ck_authors_is_viewer CHECK (is_viewer IN (0, 1))
);

COMMENT ON TABLE authors IS '게시물 작성자 조회용 사용자 요약 정보';
COMMENT ON COLUMN authors.author_id IS '작성자 ID. 현재 PostController mock userId와 연결';
COMMENT ON COLUMN authors.username IS '작성자 username';
COMMENT ON COLUMN authors.display_name IS '작성자 표시 이름';
COMMENT ON COLUMN authors.profile_image_url IS '작성자 프로필 이미지 URL';
COMMENT ON COLUMN authors.has_active_story IS '활성 스토리 여부. 0=false, 1=true';
COMMENT ON COLUMN authors.is_viewer IS '현재 조회자 본인 여부. 0=false, 1=true';

CREATE TABLE posts (
    post_id NUMBER(10) NOT NULL,
    user_id NUMBER(10) NOT NULL,
    caption VARCHAR2(2000 CHAR),
    translated_caption VARCHAR2(2000 CHAR),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_posts PRIMARY KEY (post_id),
    CONSTRAINT fk_posts_author FOREIGN KEY (user_id) REFERENCES authors (author_id)
);

COMMENT ON TABLE posts IS '게시물 기본 정보';
COMMENT ON COLUMN posts.post_id IS '게시물 ID. seq_posts로 생성';
COMMENT ON COLUMN posts.user_id IS '작성자 ID. JWT 인증 사용자 기준';
COMMENT ON COLUMN posts.caption IS '게시물 본문';
COMMENT ON COLUMN posts.translated_caption IS '번역된 게시물 본문';
COMMENT ON COLUMN posts.deleted_at IS '게시물 소프트 삭제 시각';

CREATE TABLE post_media (
    media_id NUMBER(10) NOT NULL,
    post_id NUMBER(10) NOT NULL,
    media_type VARCHAR2(20 CHAR) NOT NULL,
    media_url VARCHAR2(1000 CHAR) NOT NULL,
    sort_order NUMBER(5) DEFAULT 0 NOT NULL,
    original_file_name VARCHAR2(255 CHAR),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT pk_post_media PRIMARY KEY (media_id),
    CONSTRAINT fk_post_media_post FOREIGN KEY (post_id) REFERENCES posts (post_id),
    CONSTRAINT ck_post_media_type CHECK (media_type IN ('IMAGE', 'VIDEO')),
    CONSTRAINT uq_post_media_sort UNIQUE (post_id, sort_order)
);

COMMENT ON TABLE post_media IS '게시물 첨부 미디어';
COMMENT ON COLUMN post_media.media_id IS '미디어 ID. seq_post_media로 생성';
COMMENT ON COLUMN post_media.post_id IS '게시물 ID';
COMMENT ON COLUMN post_media.media_type IS '미디어 타입. IMAGE 또는 VIDEO';
COMMENT ON COLUMN post_media.media_url IS '미디어 접근 URL';
COMMENT ON COLUMN post_media.sort_order IS '게시물 안에서 미디어 노출 순서';
COMMENT ON COLUMN post_media.original_file_name IS '원본 파일명';

CREATE INDEX ix_posts_user_id ON posts (user_id);
CREATE INDEX ix_post_media_post_id ON post_media (post_id);

MERGE INTO authors target
USING (
    SELECT
        1 AS author_id,
        'oosu.hada' AS username,
        'oosu' AS display_name,
        '/oosu.hada.jpg' AS profile_image_url,
        1 AS has_active_story,
        1 AS is_viewer
    FROM dual
) source
ON (target.author_id = source.author_id)
WHEN MATCHED THEN
    UPDATE SET
        target.username = source.username,
        target.display_name = source.display_name,
        target.profile_image_url = source.profile_image_url,
        target.has_active_story = source.has_active_story,
        target.is_viewer = source.is_viewer,
        target.updated_at = SYSTIMESTAMP
WHEN NOT MATCHED THEN
    INSERT (
        author_id,
        username,
        display_name,
        profile_image_url,
        has_active_story,
        is_viewer
    ) VALUES (
        source.author_id,
        source.username,
        source.display_name,
        source.profile_image_url,
        source.has_active_story,
        source.is_viewer
    );
