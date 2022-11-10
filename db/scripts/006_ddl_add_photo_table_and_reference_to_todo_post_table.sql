-- Create table photo_post for storing post photos.
CREATE TABLE IF NOT EXISTS post_photo
(
    id    SERIAL PRIMARY KEY,
    photo bytea
);
comment on table post_photo is 'Photo for Post';
comment on column post_photo.id is 'Photo identifier';
comment on column post_photo.photo is 'Photo in byte[]';

-- Add FK to auto_post table.
ALTER TABLE auto_post
    ADD COLUMN post_photo_id INT REFERENCES post_photo (id);
comment on column auto_post.post_photo_id is 'FK post_photo';