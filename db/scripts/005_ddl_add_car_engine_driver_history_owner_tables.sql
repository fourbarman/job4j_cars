-- Create table engine.
CREATE TABLE IF NOT EXISTS engine
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
comment on table engine is 'Engine';
comment on column engine.id is 'Engine identifier';
comment on column engine.name is 'Engine name';

-- Create table driver.
CREATE TABLE IF NOT EXISTS driver
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    user_id INT NOT NULL REFERENCES auto_user (id)
);
comment on table driver is 'Car driver';
comment on column driver.id is 'Driver identifier';
comment on column driver.name is 'Driver name';
comment on column driver.user_id is 'FK auto_user';

-- Create table car.
CREATE TABLE IF NOT EXISTS car
(
    id        SERIAL PRIMARY KEY,
    name      TEXT,
    driver_id INT NOT NULL REFERENCES driver (id),
    engine_id INT NOT NULL REFERENCES engine (id)
);
comment on table car is 'Car';
comment on column car.id is 'Car identifier';
comment on column car.name is 'Car name';
comment on column car.driver_id is 'FK driver';
comment on column car.engine_id is 'FK engine';

-- Alter table auto_post add FK car.
ALTER TABLE auto_post
    ADD COLUMN car_id INT REFERENCES car (id);
comment on column auto_post.car_id is 'FK car';

-- Create table history_owner.
CREATE TABLE IF NOT EXISTS history_owner
(
    id        SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver (id),
    car_id    INT NOT NULL REFERENCES car (id),
    start_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    end_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);
comment on table history_owner is 'Car owner history';
comment on column history_owner.id is 'Car owner identifier';
comment on column history_owner.driver_id is 'FK driver';
comment on column history_owner.car_id is 'FK car';
comment on column history_owner.start_at is 'Ownership start timestamp';
comment on column history_owner.end_at is 'Ownership end timestamp';