ALTER TABLE notifications
    ADD (
        source_type VARCHAR2(30 CHAR),
        source_id   NUMBER(19)
    );

CREATE INDEX idx_notifications_source
    ON notifications (source_type, source_id);
