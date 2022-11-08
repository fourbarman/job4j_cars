/* Author: fourbarman */

/*
Create tables auto_user, auto_post.
*/

CREATE TABLE IF NOT EXISTS auto_user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    CONSTRAINT login_unique UNIQUE (login)
);
comment on table auto_user is 'System user';
comment on column auto_user.id is 'User identifier';
comment on column auto_user.login is 'User login, unique';
comment on column auto_user.password is 'User password';

CREATE TABLE IF NOT EXISTS auto_post
(
    id           SERIAL PRIMARY KEY,
    text         TEXT                        NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    auto_user_id INT                         NOT NULL REFERENCES auto_user (id)
);
comment on table auto_post is 'Table for Posts';
comment on column auto_post.id is 'Post identifier';
comment on column auto_post.text is 'Post description';
comment on column auto_post.created is 'Post created timestamp';
comment on column auto_post.auto_user_id is 'FK for auto_user table';



