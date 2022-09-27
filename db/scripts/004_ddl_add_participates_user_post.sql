/* Author: fourbarman */

/*
Создание таблицы participates.
*/
create table if not exists participates
(
    id           serial primary key,
    auto_user_id int not null references auto_user (id),
    auto_post_id int not null references auto_post (id)
);