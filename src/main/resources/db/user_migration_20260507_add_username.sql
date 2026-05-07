DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM user_tab_columns
    WHERE table_name = 'USERS'
      AND column_name = 'USERNAME';

    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'ALTER TABLE users ADD username VARCHAR2(80 CHAR)';
        EXECUTE IMMEDIATE q'[
            UPDATE users
            SET username = 'user_' || user_id
            WHERE username IS NULL
        ]';
        EXECUTE IMMEDIATE 'ALTER TABLE users MODIFY username VARCHAR2(80 CHAR) NOT NULL';
    END IF;
END;
/

DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM user_constraints
    WHERE table_name = 'USERS'
      AND constraint_name = 'UQ_USERS_USERNAME';

    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'ALTER TABLE users ADD CONSTRAINT uq_users_username UNIQUE (username)';
    END IF;
END;
/
