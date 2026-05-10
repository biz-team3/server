CREATE TABLE likes
(
    like_id    NUMBER(19)  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    NUMBER(19)  NOT NULL,
    post_id    NUMBER(19)  NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts (post_id),
    CONSTRAINT uk_likes_user_post UNIQUE (user_id, post_id)
);