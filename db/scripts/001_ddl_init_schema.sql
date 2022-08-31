/* Author: fourbarman */

/*
Создание таблиц.
*/

CREATE TABLE IF NOT EXISTS auto_user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    CONSTRAINT login_unique UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS auto_post
(
    id           SERIAL PRIMARY KEY,
    text         INT NOT NULL,
    created      INT NOT NULL,
    auto_user_id INT NOT NULL REFERENCES auto_user(id)
);



