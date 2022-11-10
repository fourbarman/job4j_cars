-- Create table car_brand for storing car brand.
CREATE TABLE IF NOT EXISTS brand
(
    id    SERIAL PRIMARY KEY,
    name TEXT
);
comment on table brand is 'Car brand';
comment on column brand.id is 'Car brand identifier';
comment on column brand.name is 'Car brand name';

-- Add FK to car table.
ALTER TABLE car
    ADD COLUMN brand_id INT REFERENCES brand (id);
comment on column car.brand_id is 'FK car_brand';