/* Author: fourbarman */

/*
1. Create table photo_post for storing post photos.
2. Add FK to auto_post table.
Relation: photo_post : many-to-one : auto_post
*/

CREATE TABLE IF NOT EXISTS post_photo
(
    id    SERIAL PRIMARY KEY,
    photo bytea
);
comment on table post_photo is 'Photo for Post';
comment on column post_photo.id is 'Photo identifier';
comment on column post_photo.photo is 'Photo in byte[]';

ALTER TABLE auto_post
    ADD COLUMN post_photo_id INT REFERENCES post_photo (id);
comment on column auto_post.post_photo_id is 'FK post_photo';