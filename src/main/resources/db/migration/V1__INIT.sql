CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE refresh_token
(
    id          BIGINT      NOT NULL,
    token       VARCHAR(128),
    subject     VARCHAR(50) NOT NULL,
    enabled     BOOLEAN     NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    expired_at  TIMESTAMP   NOT NULL,
    replaced_by VARCHAR(128),
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE TABLE user_authentication
(
    id                    BIGINT      NOT NULL,
    username              VARCHAR(30) NOT NULL,
    password              VARCHAR(32) NOT NULL,
    personal_id           BIGINT      NOT NULL,
    last_password_changed date        NOT NULL,
    CONSTRAINT pk_user_authentication PRIMARY KEY (id)
);

CREATE TABLE user_authority
(
    id        BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    authority VARCHAR(50),
    CONSTRAINT pk_user_authority PRIMARY KEY (id)
);

CREATE TABLE user_locked
(
    id          BIGINT       NOT NULL,
    message     VARCHAR(100) NOT NULL,
    user_id     BIGINT       NOT NULL,
    locked_from TIMESTAMP,
    locked_to   TIMESTAMP,
    CONSTRAINT pk_user_locked PRIMARY KEY (id)
);

CREATE TABLE user_personal
(
    id         BIGINT  NOT NULL,
    name       VARCHAR(30),
    created_at TIMESTAMP,
    deleted    BOOLEAN NOT NULL,
    CONSTRAINT pk_user_personal PRIMARY KEY (id)
);

ALTER TABLE refresh_token
    ADD CONSTRAINT uc_refresh_token_token UNIQUE (token);

ALTER TABLE user_authentication
    ADD CONSTRAINT uc_user_authentication_personal UNIQUE (personal_id);

ALTER TABLE user_authentication
    ADD CONSTRAINT uc_user_authentication_username UNIQUE (username);

CREATE INDEX idx_replaced ON refresh_token (replaced_by);

CREATE INDEX idx_subject ON refresh_token (subject);

CREATE INDEX idx_token ON refresh_token (token);

CREATE INDEX idx_uniq_username ON user_authentication (username);

ALTER TABLE user_authentication
    ADD CONSTRAINT FK_USER_AUTHENTICATION_ON_PERSONAL FOREIGN KEY (personal_id) REFERENCES user_personal (id);

CREATE INDEX idx_uniq_personal ON user_authentication (personal_id);

ALTER TABLE user_authority
    ADD CONSTRAINT FK_USER_AUTHORITY_ON_USER FOREIGN KEY (user_id) REFERENCES user_personal (id);

ALTER TABLE user_locked
    ADD CONSTRAINT FK_USER_LOCKED_ON_USER FOREIGN KEY (user_id) REFERENCES user_personal (id);