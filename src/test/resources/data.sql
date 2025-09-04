-- ===== POST 기본 데이터: boardId=10에 40개 (삭제 5개 포함) =====
DELETE FROM POST_HASHTAG;
DELETE FROM POST_LIKE;
DELETE FROM COMMENT;
DELETE FROM HASHTAG;
DELETE FROM POST;

-- POST(POST_ID, POST_TYPE, TITLE, CONTENT, CREATED_AT, UPDATED_AT, VIEW_COUNT, IS_DELETED, MEMBER_ID, NICKNAME, BOARD_ID)
INSERT INTO POST VALUES
                     (1,  'MEMBER', 'Hello Spring',     'first content',      TIMESTAMP '2025-02-01 10:00:00', TIMESTAMP '2025-02-01 12:00:00', 5,  FALSE, 101, NULL,      10),
                     (2,  'GUEST',  'Guest Post',        'mybatis content',    TIMESTAMP '2025-02-02 10:00:00', NULL,                             3,  FALSE, NULL, '게스트', 10),
                     (3,  'MEMBER', 'Deleted One',       'ignore me',          TIMESTAMP '2025-02-03 10:00:00', NULL,                             0,  TRUE,  202, NULL,      10),
                     (4,  'MEMBER', 'Spring Tips',       'tips',               TIMESTAMP '2025-02-04 10:00:00', NULL,                             1,  FALSE, 102, NULL,      10),
                     (5,  'GUEST',  'Guide',             'my guide',           TIMESTAMP '2025-02-05 10:00:00', NULL,                             2,  FALSE, NULL, '나그네', 10),
                     (6,  'GUEST',  'Guest Story 6',     'content 6',          TIMESTAMP '2025-02-06 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (7,  'MEMBER', 'Member Note 7',     'content 7',          TIMESTAMP '2025-02-07 10:00:00', NULL,                             0,  FALSE, 107, NULL,      10),
                     (8,  'MEMBER', 'Deleted Two',       'content 8',          TIMESTAMP '2025-02-08 10:00:00', NULL,                             0,  TRUE,  108, NULL,      10),
                     (9,  'MEMBER', 'Member Note 9',     'content 9',          TIMESTAMP '2025-02-09 10:00:00', NULL,                             0,  FALSE, 109, NULL,      10),
                     (10, 'GUEST',  'Guest Story 10',    'content 10',         TIMESTAMP '2025-02-10 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (11, 'MEMBER', 'Member Note 11',    'content 11',         TIMESTAMP '2025-02-11 10:00:00', NULL,                             0,  FALSE, 111, NULL,      10),
                     (12, 'MEMBER', 'Spring Deep Dive',  'content 12',         TIMESTAMP '2025-02-12 10:00:00', NULL,                             0,  FALSE, 112, NULL,      10),
                     (13, 'MEMBER', 'Deleted Three',     'content 13',         TIMESTAMP '2025-02-13 10:00:00', NULL,                             0,  TRUE,  113, NULL,      10),
                     (14, 'GUEST',  'Guest Story 14',    'content 14',         TIMESTAMP '2025-02-14 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (15, 'MEMBER', 'Member Note 15',    'content 15',         TIMESTAMP '2025-02-15 10:00:00', NULL,                             0,  FALSE, 115, NULL,      10),
                     (16, 'MEMBER', 'my note 16',        'my note 16',         TIMESTAMP '2025-02-16 10:00:00', NULL,                             0,  FALSE, 116, NULL,      10),
                     (17, 'MEMBER', 'Member Note 17',    'content 17',         TIMESTAMP '2025-02-17 10:00:00', NULL,                             0,  FALSE, 117, NULL,      10),
                     (18, 'GUEST',  'Guest Story 18',    'content 18',         TIMESTAMP '2025-02-18 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (19, 'MEMBER', 'Member Note 19',    'content 19',         TIMESTAMP '2025-02-19 10:00:00', NULL,                             0,  FALSE, 119, NULL,      10),
                     (20, 'MEMBER', 'Spring Handbook',   'content 20',         TIMESTAMP '2025-02-20 10:00:00', NULL,                             0,  FALSE, 120, NULL,      10),
                     (21, 'MEMBER', 'Deleted Four',      'content 21',         TIMESTAMP '2025-02-21 10:00:00', NULL,                             0,  TRUE,  121, NULL,      10),
                     (22, 'GUEST',  'Guest Story 22',    'content 22',         TIMESTAMP '2025-02-22 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (23, 'MEMBER', 'Member Note 23',    'content 23',         TIMESTAMP '2025-02-23 10:00:00', NULL,                             0,  FALSE, 123, NULL,      10),
                     (24, 'MEMBER', 'my day 24',         'my day 24',          TIMESTAMP '2025-02-24 10:00:00', NULL,                             0,  FALSE, 124, NULL,      10),
                     (25, 'MEMBER', 'Member Note 25',    'content 25',         TIMESTAMP '2025-02-25 10:00:00', NULL,                             0,  FALSE, 125, NULL,      10),
                     (26, 'GUEST',  'Guest Story 26',    'content 26',         TIMESTAMP '2025-02-26 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (27, 'MEMBER', 'Member Note 27',    'content 27',         TIMESTAMP '2025-02-27 10:00:00', NULL,                             0,  FALSE, 127, NULL,      10),
                     (28, 'MEMBER', 'Spring Patterns',   'content 28',         TIMESTAMP '2025-02-28 10:00:00', NULL,                             0,  FALSE, 128, NULL,      10),
                     (29, 'MEMBER', 'Member Note 29',    'content 29',         TIMESTAMP '2025-03-01 10:00:00', NULL,                             0,  FALSE, 129, NULL,      10),
                     (30, 'GUEST',  'Guest Story 30',    'content 30',         TIMESTAMP '2025-03-02 10:00:00', NULL,                             0,  FALSE, NULL, '게스트', 10),
                     (31, 'MEMBER', 'Member Note 31',    'content 31',         TIMESTAMP '2025-03-03 10:00:00', NULL,                             0,  FALSE, 131, NULL,      10),
                     (32, 'MEMBER', 'Member Note 32',    'content 32',         TIMESTAMP '2025-03-04 10:00:00', NULL,                             0,  FALSE, 132, NULL,      10),
                     (33, 'MEMBER', 'my cat 33',         'my cat 33',          TIMESTAMP '2025-03-05 10:00:00', NULL,                             0,  FALSE, 133, NULL,      10),
                     (34, 'GUEST',  'Deleted Five',      'content 34',         TIMESTAMP '2025-03-06 10:00:00', NULL,                             0,  TRUE,  NULL, '게스트', 10),
                     (35, 'MEMBER', 'Member Note 35',    'content 35',         TIMESTAMP '2025-03-07 10:00:00', NULL,                             0,  FALSE, 135, NULL,      10),
                     (36, 'MEMBER', 'Spring Boot 36',    'content 36',         TIMESTAMP '2025-03-08 10:00:00', NULL,                             0,  FALSE, 136, NULL,      10),
                     (37, 'MEMBER', 'Member Note 37',    'content 37',         TIMESTAMP '2025-03-09 10:00:00', NULL,                             0,  FALSE, 137, NULL,      10),
                     (38, 'MEMBER', 'Member Note 38',    'content 38',         TIMESTAMP '2025-03-10 10:00:00', NULL,                             0,  FALSE, 138, NULL,      10),
                     (39, 'MEMBER', 'Member Note 39',    'content 39',         TIMESTAMP '2025-03-11 10:00:00', NULL,                             0,  FALSE, 139, NULL,      10),
                     (40, 'MEMBER', 'Member Note 40',    'content 40',         TIMESTAMP '2025-03-12 10:00:00', NULL,                             0,  FALSE, 140, NULL,      10);

-- 좋아요: post 1은 memberId 101,102 / post 2는 guest vid=v-3
INSERT INTO POST_LIKE (POSTLIKE_ID, VID, LIKED_MEMBER_ID, POST_ID) VALUES
                                                                       (1, 'v-1', 101, 1),
                                                                       (2, 'v-2', 102, 1),
                                                                       (3, 'v-3', NULL, 2);

-- 댓글: ACTIVE만 집계. 총 8개 ACTIVE
-- post 1: ACTIVE 2 + DELETED 1
INSERT INTO COMMENT (COMMENT_ID, PARENT_ID, POST_ID, CONTENT, COMMENT_STATE, CREATED_AT, UPDATED_AT) VALUES
                                                                                                         (1, NULL, 1, 'c1', 'ACTIVE',  TIMESTAMP '2025-02-01 11:00:00', NULL),
                                                                                                         (2, NULL, 1, 'c2', 'ACTIVE',  TIMESTAMP '2025-02-01 11:10:00', NULL),
                                                                                                         (3, NULL, 1, 'c3', 'DELETED', TIMESTAMP '2025-02-01 11:20:00', NULL);

-- post 2: ACTIVE 1
INSERT INTO COMMENT VALUES
    (4, NULL, 2, 'c4', 'ACTIVE', TIMESTAMP '2025-02-02 11:00:00', NULL);

-- post 10: ACTIVE 3
INSERT INTO COMMENT VALUES
                        (5, NULL, 10, 'c10-1', 'ACTIVE', TIMESTAMP '2025-02-10 11:00:00', NULL),
                        (6, NULL, 10, 'c10-2', 'ACTIVE', TIMESTAMP '2025-02-10 11:05:00', NULL),
                        (7, NULL, 10, 'c10-3', 'ACTIVE', TIMESTAMP '2025-02-10 11:10:00', NULL);

-- post 15: ACTIVE 2
INSERT INTO COMMENT VALUES
                        (8, NULL, 15, 'c15-1', 'ACTIVE', TIMESTAMP '2025-02-15 11:00:00', NULL),
                        (9, NULL, 15, 'c15-2', 'ACTIVE', TIMESTAMP '2025-02-15 11:05:00', NULL);

-- 해시태그 사전
MERGE INTO HASHTAG (HASHTAG_ID, TAG) KEY (HASHTAG_ID) VALUES (1, 'java');
MERGE INTO HASHTAG (HASHTAG_ID, TAG) KEY (HASHTAG_ID) VALUES (2, 'spring');
MERGE INTO HASHTAG (HASHTAG_ID, TAG) KEY (HASHTAG_ID) VALUES (3, 'mybatis');

-- 태그 매핑
-- java: 1,4,12,20,28,36
INSERT INTO POST_HASHTAG (POST_ID, HASHTAG_ID) VALUES (1,1),(4,1),(12,1),(20,1),(28,1),(36,1);
-- spring: 1,12
INSERT INTO POST_HASHTAG (POST_ID, HASHTAG_ID) VALUES (1,2),(12,2);
-- mybatis: 2,7,14,22
INSERT INTO POST_HASHTAG (POST_ID, HASHTAG_ID) VALUES (2,3),(7,3),(14,3),(22,3);
