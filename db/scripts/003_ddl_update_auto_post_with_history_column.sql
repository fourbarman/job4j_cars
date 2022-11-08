/* Author: fourbarman */

/*
Alter table auto_post.
Add reference to history_table table.
*/

alter table auto_post
    add column price_history_id int references price_history (id);

comment on column auto_post.price_history_id is 'FK price_history';