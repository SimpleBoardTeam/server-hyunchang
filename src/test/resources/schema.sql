DROP TABLE IF EXISTS POST_HASHTAG;
DROP TABLE IF EXISTS POST_LIKE;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS HASHTAG;
DROP TABLE IF EXISTS POST;

CREATE TABLE POST (
                      POST_ID     BIGINT AUTO_INCREMENT PRIMARY KEY,
                      POST_TYPE   VARCHAR(32) NOT NULL,         -- 'MEMBER' | 'GUEST' (MyBatis 매핑용)
                      TITLE       VARCHAR(255) NOT NULL,
                      CONTENT     VARCHAR(10000) NOT NULL,
                      CREATED_AT  TIMESTAMP    NOT NULL,
                      UPDATED_AT  TIMESTAMP,
                      VIEW_COUNT  BIGINT       NOT NULL,
                      IS_DELETED  BOOLEAN      NOT NULL,
                      MEMBER_ID   BIGINT,
                      NICKNAME    VARCHAR(255),
                      BOARD_ID    BIGINT       NOT NULL
);

CREATE TABLE POST_LIKE (
                           POSTLIKE_ID     BIGINT AUTO_INCREMENT PRIMARY KEY,
                           VID             VARCHAR(255) NOT NULL,
                           LIKED_MEMBER_ID BIGINT,
                           POST_ID         BIGINT NOT NULL,
                           CONSTRAINT FK_POSTLIKE_POST FOREIGN KEY (POST_ID) REFERENCES POST(POST_ID)
);

CREATE TABLE COMMENT (
                         COMMENT_ID    BIGINT AUTO_INCREMENT PRIMARY KEY,
                         PARENT_ID     BIGINT,
                         POST_ID       BIGINT NOT NULL,
                         CONTENT       VARCHAR(1000) NOT NULL,
                         COMMENT_STATE VARCHAR(32)   NOT NULL,   -- 'ACTIVE' 등 (MyBatis XML 기준)
                         CREATED_AT    TIMESTAMP,
                         UPDATED_AT    TIMESTAMP
);

CREATE TABLE HASHTAG (
                         HASHTAG_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                         TAG        VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE POST_HASHTAG (
                              POST_ID     BIGINT NOT NULL,
                              HASHTAG_ID  BIGINT NOT NULL,
                              PRIMARY KEY (POST_ID, HASHTAG_ID),
                              CONSTRAINT FK_PH_POST FOREIGN KEY (POST_ID) REFERENCES POST(POST_ID),
                              CONSTRAINT FK_PH_TAG  FOREIGN KEY (HASHTAG_ID) REFERENCES HASHTAG(HASHTAG_ID)
);
