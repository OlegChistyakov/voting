DELETE FROM dishes;
DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User#1', 'user@gmail.com', '{noop}password'),
       ('User#2', 'user@yandex.ru', '{noop}password'),
       ('Admin#1', 'admin@gmail.com', '{noop}admin'),
       ('Admin#2', 'admin@yandex.ru', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100002),
       ('USER', 100002),
       ('ADMIN', 100003);

INSERT INTO restaurants (name, address, user_id)
VALUES ('RestaurantName1', 'address1', 100002),
       ('RestaurantName2', 'address2', 100002),
       ('RestaurantName3', 'address3', 100003);

INSERT INTO dishes (rest_id, date, description, price)
VALUES (100004, now() - 1, 'yesterday''s dish_1 from rest_1', 546),
       (100004, now(), 'today''s dish_1 from rest_1', 432),
       (100004, now() - 1, 'yesterday''s dish_2 from rest_1', 100),
       (100004, now(), 'today''s dish_2 from rest_1', 1000),
       (100005, now() - 2, 'before yesterday''s dish_1 from rest_2', 250),
       (100005, now() - 2, 'before yesterday''s dish_2 from rest_2', 500),
       (100005, now(), 'today''s dish_1 from rest_2', 543),
       (100005, now(), 'today''s dish_2 from rest_2', 10),
       (100006, now() - 10, 'very old dish', 1000);

INSERT INTO votes (date, user_id, rest_id)
VALUES (now(), 100000, 100004),
       (now() - 10, 100000, 100005),
       (now(), 100001, 100004),
       (now() - 2, 100001, 100005);