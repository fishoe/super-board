CREATE TABLE article
(
    id            BIGINT      NOT NULL,
    subject       VARCHAR(120),
    author_id     BIGINT,
    author_name   VARCHAR(20) NOT NULL,
    context       CLOB        NOT NULL,
    created_at    TIMESTAMP   NOT NULL,
    modified_at   TIMESTAMP   NOT NULL,
    status        INT         NOT NULL,
    group_main_id BIGINT      NOT NULL,
    group_sub_id  BIGINT,
    password      VARCHAR(200),
    CONSTRAINT pk_article PRIMARY KEY (id)
);

CREATE TABLE article_group_main
(
    id             BIGINT      NOT NULL,
    path           VARCHAR(16) NOT NULL,
    name           VARCHAR(20) NOT NULL,
    created_at     TIMESTAMP   NOT NULL,
    type           INT         NOT NULL,
    visible        BOOLEAN     NOT NULL,
    etc_group_name VARCHAR(16),
    CONSTRAINT pk_article_group_main PRIMARY KEY (id)
);

CREATE TABLE article_group_main_role
(
    id                    BIGINT  NOT NULL,
    article_group_main_id BIGINT  NOT NULL,
    catalog_authority     INT     NOT NULL,
    read_authority        INT     NOT NULL,
    write_authority       INT     NOT NULL,
    deletable             BOOLEAN NOT NULL,
    modifiable            BOOLEAN NOT NULL,
    CONSTRAINT pk_article_group_main_role PRIMARY KEY (id)
);

CREATE TABLE article_group_sub
(
    id         BIGINT      NOT NULL,
    main_id    BIGINT      NOT NULL,
    name       VARCHAR(16) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    code       VARCHAR(20) NOT NULL,
    CONSTRAINT pk_article_group_sub PRIMARY KEY (id)
);

CREATE TABLE article_group_sub_role
(
    id              BIGINT  NOT NULL,
    group_id        BIGINT  NOT NULL,
    write_authority INT     NOT NULL,
    read_authority  INT     NOT NULL,
    deletable       BOOLEAN NOT NULL,
    modifiable      BOOLEAN NOT NULL,
    CONSTRAINT pk_article_group_sub_role PRIMARY KEY (id)
);

CREATE TABLE article_modified_log
(
    id           BIGINT    NOT NULL,
    article_id   BIGINT    NOT NULL,
    subject      VARCHAR(120),
    context      CLOB,
    created_at   TIMESTAMP NOT NULL,
    group_sub_id BIGINT,
    CONSTRAINT pk_article_modified_log PRIMARY KEY (id)
);

CREATE TABLE article_tag
(
    id         BIGINT NOT NULL,
    tag        VARCHAR(40),
    article_id BIGINT NOT NULL,
    CONSTRAINT pk_article_tag PRIMARY KEY (id)
);

CREATE TABLE article_view_log
(
    id         BIGINT      NOT NULL,
    article_id BIGINT      NOT NULL,
    session    VARCHAR(64) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    CONSTRAINT pk_article_view_log PRIMARY KEY (id)
);

CREATE TABLE article_vote
(
    id         BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    article_id BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL,
    vote_up    BOOLEAN   NOT NULL,
    CONSTRAINT pk_article_vote PRIMARY KEY (id)
);

CREATE TABLE reply
(
    id               BIGINT      NOT NULL,
    article_id       BIGINT      NOT NULL,
    author_id        BIGINT      NOT NULL,
    author_name      VARCHAR(40) NOT NULL,
    created_at       TIMESTAMP   NOT NULL,
    last_modified_at TIMESTAMP,
    context          CLOB        NOT NULL,
    deleted          BOOLEAN     NOT NULL,
    CONSTRAINT pk_reply PRIMARY KEY (id)
);

CREATE TABLE reply_modified_log
(
    id         BIGINT    NOT NULL,
    reply_id   BIGINT    NOT NULL,
    context    CLOB,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_reply_modified_log PRIMARY KEY (id)
);

CREATE TABLE reply_vote
(
    id         BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    reply_id   BIGINT    NOT NULL,
    vote_up    BOOLEAN   NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_reply_vote PRIMARY KEY (id)
);

ALTER TABLE article_group_main
    ADD CONSTRAINT uc_article_group_main_name UNIQUE (name);

ALTER TABLE article_group_main
    ADD CONSTRAINT uc_article_group_main_path UNIQUE (path);

ALTER TABLE article_view_log
    ADD CONSTRAINT uc_article_view_log_article_session UNIQUE (article_id, session);

ALTER TABLE article_vote
    ADD CONSTRAINT uc_article_vote_user_article UNIQUE (user_id, article_id);

ALTER TABLE article_group_sub
    ADD CONSTRAINT uc_articlegroupsub_main_code UNIQUE (main_id, code);

ALTER TABLE reply_vote
    ADD CONSTRAINT uc_reply_vote_user_reply UNIQUE (user_id, reply_id);

ALTER TABLE article_tag
    ADD CONSTRAINT uc_tag_article_tag UNIQUE (tag, article_id);

CREATE INDEX idx_articlegroupmain_path ON article_group_main (path);

ALTER TABLE article_group_main_role
    ADD CONSTRAINT FK_ARTICLE_GROUP_MAIN_ROLE_ON_ARTICLE_GROUP_MAIN FOREIGN KEY (article_group_main_id) REFERENCES article_group_main (id);

ALTER TABLE article_group_sub
    ADD CONSTRAINT FK_ARTICLE_GROUP_SUB_ON_MAIN FOREIGN KEY (main_id) REFERENCES article_group_main (id);

ALTER TABLE article_group_sub_role
    ADD CONSTRAINT FK_ARTICLE_GROUP_SUB_ROLE_ON_GROUP FOREIGN KEY (group_id) REFERENCES article_group_sub (id);

ALTER TABLE article_modified_log
    ADD CONSTRAINT FK_ARTICLE_MODIFIED_LOG_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_modified_log
    ADD CONSTRAINT FK_ARTICLE_MODIFIED_LOG_ON_GROUP_SUB FOREIGN KEY (group_sub_id) REFERENCES article_group_sub (id);

ALTER TABLE article
    ADD CONSTRAINT FK_ARTICLE_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES user_personal (id);

ALTER TABLE article
    ADD CONSTRAINT FK_ARTICLE_ON_GROUP_MAIN FOREIGN KEY (group_main_id) REFERENCES article_group_main (id);

ALTER TABLE article
    ADD CONSTRAINT FK_ARTICLE_ON_GROUP_SUB FOREIGN KEY (group_sub_id) REFERENCES article_group_sub (id);

ALTER TABLE article_tag
    ADD CONSTRAINT FK_ARTICLE_TAG_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_view_log
    ADD CONSTRAINT FK_ARTICLE_VIEW_LOG_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_vote
    ADD CONSTRAINT FK_ARTICLE_VOTE_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_vote
    ADD CONSTRAINT FK_ARTICLE_VOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user_personal (id);

ALTER TABLE reply_modified_log
    ADD CONSTRAINT FK_REPLY_MODIFIED_LOG_ON_REPLY FOREIGN KEY (reply_id) REFERENCES reply (id);

ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES user_personal (id);

ALTER TABLE reply_vote
    ADD CONSTRAINT FK_REPLY_VOTE_ON_REPLY FOREIGN KEY (reply_id) REFERENCES reply (id);

ALTER TABLE reply_vote
    ADD CONSTRAINT FK_REPLY_VOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user_personal (id);