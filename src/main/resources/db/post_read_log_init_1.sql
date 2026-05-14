CREATE TABLE post_read_logs
(
    log_id  NUMBER(19)  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id NUMBER(19)  NOT NULL,
    post_id NUMBER(19)  NOT NULL,
    read_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT fk_read_logs_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_read_logs_post FOREIGN KEY (post_id) REFERENCES posts (post_id) ON DELETE CASCADE,
    CONSTRAINT uk_read_logs_user_post UNIQUE (user_id, post_id)
);