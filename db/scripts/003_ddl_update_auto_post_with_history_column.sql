/* Author: fourbarman */

/*
Создание таблиц.
*/

alter table auto_post
    add column history_table_id int references price_history (id);