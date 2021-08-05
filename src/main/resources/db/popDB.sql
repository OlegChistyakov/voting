DELETE FROM  dishes;
DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name, address)
VALUES ('RestaurantName1', 'address1'),
       ('RestaurantName2', 'address2'),
       ('RestaurantName3', 'address3');

INSERT INTO dishes (rest_id, date, description, price)
VALUES (100000, date 'now()' - integer '1', 'yesterday''s dish_1 from rest_1', 546),
       (100000, date 'now()', 'today''s dish_1 from rest_1', 432),
       (100000, date 'now()' - integer '1', 'yesterday''s dish_2 from rest_1', 100),
       (100000, date 'now()', 'today''s dish_2 from rest_1', 1000),
       (100001, date 'now()' - integer '2', 'before yesterday''s dish_1 from rest_2', 250),
       (100001, date 'now()' - integer '2', 'before yesterday''s dish_2 from rest_2', 500),
       (100001, date 'now()', 'today''s dish_1 from rest_2', 543),
       (100001, date 'now()', 'today''s dish_2 from rest_2', 10),
       (100002, date 'now()' - integer '10', 'very old dish', 1000);

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100012),
       ('ADMIN', 100013),
       ('USER', 100013);

INSERT INTO votes (date, user_id, rest_id)
VALUES (date 'now()', 100012, 100000),
       (date 'now()' - integer '10', 100012, 100002),
       (date 'now()', 100013, 100000),
       (date 'now()' - integer '2', 100013, 100001);