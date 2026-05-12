CREATE TABLE token_blacklist
(
    blacklist_id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    jti          VARCHAR2(100) NOT NULL,
    user_id      NUMBER(19),
    expires_at   TIMESTAMP                      NOT NULL,
    created_at   TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT uk_token_blacklist_jti UNIQUE (jti),
    CONSTRAINT fk_token_blacklist_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);