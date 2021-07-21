DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE restaurants
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR NOT NULL,
    address VARCHAR NOT NULL
);

CREATE TABLE dishes
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    rest_id     INTEGER NOT NULL,
    date        DATE    NOT NULL,
    description VARCHAR NOT NULL,
    price       INTEGER NOT NULL,
    CONSTRAINT rest_id_description_idx UNIQUE (date, description),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);