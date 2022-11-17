-- -- Alter table auto_post.
-- alter table auto_post
--     add column price_history_id int references price_history (id);
-- -- Add reference to history_table table.
-- comment on column auto_post.price_history_id is 'FK price_history';

alter table price_history
    add column post_id int references auto_post (id);