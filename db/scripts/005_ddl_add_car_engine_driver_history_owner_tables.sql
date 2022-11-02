/* Author: fourbarman */

/*
Add FK to auto_post.
Create car, engine, driver, owner_history tables.
*/
ALTER TABLE auto_post ADD COLUMN car_id INT REFERENCES car(id);

CREATE TABLE IF NOT EXISTS engine
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS car
(
    id        SERIAL PRIMARY KEY,
    name      TEXT,
    driver_id INT NOT NULL REFERENCES driver(id),
    engine_id INT NOT NULL REFERENCES engine (id)
);

CREATE TABLE IF NOT EXISTS driver
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    user_id INT NOT NULL REFERENCES auto_user (id)
);

CREATE TABLE IF NOT EXISTS history_owner
(
    id        SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver (id),
    car_id    INT NOT NULL REFERENCES car (id),
    start_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    end_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);