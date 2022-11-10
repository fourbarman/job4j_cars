-- Create tables price_history
create table if not exists price_history
(
    id      serial primary key,
    before  bigint not null,
    after   bigint not null,
    created timestamp without time zone default now()
);

comment on table price_history is 'System user';
comment on column price_history.id is 'Price history identifier';
comment on column price_history.before is 'Price before';
comment on column price_history.after is 'Price after';
comment on column price_history.created is 'Timestamp';