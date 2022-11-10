--create table participates
create table if not exists participates
(
    id           serial primary key,
    auto_user_id int not null references auto_user (id),
    auto_post_id int not null references auto_post (id)
);

comment on table participates is 'Participates on Post';
comment on column participates.id is 'Record identifier';
comment on column participates.auto_user_id is 'FK auto_user';
comment on column participates.auto_post_id is 'FK auto_post';