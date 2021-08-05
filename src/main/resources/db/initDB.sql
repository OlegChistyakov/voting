DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE restaurants
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR NOT NULL,
    address VARCHAR NOT NULL,
    CONSTRAINT name_address_idx UNIQUE (name, address)
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

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR                           NOT NULL,
    email      VARCHAR                           NOT NULL,
    password   VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL,
    enabled    BOOL                DEFAULT TRUE  NOT NULL
);

CREATE TABLE votes
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date    DATE    NOT NULL,
    user_id INTEGER NOT NULL,
    rest_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id)
);

CREATE UNIQUE INDEX votes_unique_date_user_idx ON votes (user_id, date);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);