-- 기존 seed/mock 번역값을 비워 DeepL 최초 요청 시점에 실제 번역 캐시가 채워지게 한다.
UPDATE posts
SET translated_caption = NULL
WHERE translated_caption IS NOT NULL;
