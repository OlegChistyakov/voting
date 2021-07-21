DELETE FROM  dishes;
DELETE FROM  restaurants;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name, address)
VALUES ('RestaurantName1', 'address1'),
       ('RestaurantName2', 'address2');

INSERT INTO dishes (rest_id, date, description, price)
VALUES (100000, date 'now()' - integer '1', 'dishName1_1', 546),
       (100000, date 'now()', 'dishName1_2', 432),
       (100000, date 'now()' - integer '1', 'dishName1_3', 100),
       (100000, date 'now()', 'dishName1_4', 1000),
       (100001, date 'now()' - integer '2', 'dishName2_1', 250),
       (100001, date 'now()' - integer '2', 'dishName2_2', 500),
       (100001, date 'now()', 'dishName2_3', 543),
       (100001, date 'now()', 'dishName2_4', 10);