DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOL      DEFAULT TRUE  NOT NULL
);

CREATE TABLE restaurants
(
    id      INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name    VARCHAR NOT NULL,
    address VARCHAR NOT NULL,
    CONSTRAINT name_idx UNIQUE (name)
);

CREATE TABLE dishes
(
    id          INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    rest_id     INTEGER NOT NULL,
    date        DATE    NOT NULL,
    description VARCHAR NOT NULL,
    price       INTEGER NOT NULL,
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX dishes_unique_rest_date_description_idx ON dishes (rest_id, date, description);

CREATE TABLE votes
(
    id      INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    date    DATE    NOT NULL,
    user_id INTEGER NOT NULL,
    rest_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX votes_unique_date_user_idx ON votes (user_id, date);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);