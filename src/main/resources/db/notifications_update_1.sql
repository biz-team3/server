ALTER TABLE notifications
    DROP CONSTRAINT ck_notifications_type;

ALTER TABLE notifications
    ADD CONSTRAINT ck_notifications_type
        CHECK (notification_type IN ('LIKE', 'COMMENT', 'FOLLOW'));
