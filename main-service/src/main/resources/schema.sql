DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    USER_NAME VARCHAR(255)                            NOT NULL,
    EMAIL     VARCHAR(512)                            NOT NULL,
    CONSTRAINT PK_USER PRIMARY KEY (USER_ID),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (EMAIL)
);