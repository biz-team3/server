-- ============================================================
-- follow request HTTP 테스트용 seed
-- 현재 FollowRequestNotificationController 의 임시 loginUserId = 2 기준
-- user_id 3 이 user_id 2 에게 보낸 PENDING 요청을 생성함
-- ============================================================

INSERT INTO follow_requests (
    requester_user_id,
    receiver_user_id,
    status,
    created_at,
    updated_at
)
SELECT
    3,
    2,
    'PENDING',
    SYSTIMESTAMP,
    SYSTIMESTAMP
FROM dual
WHERE EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = 3
      AND delete_at IS NULL
)
  AND EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = 2
      AND delete_at IS NULL
)
  AND NOT EXISTS (
    SELECT 1
    FROM follow_requests
    WHERE requester_user_id = 3
      AND receiver_user_id = 2
      AND status = 'PENDING'
);

COMMIT;
