package ru.graduation.voting.repository;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.graduation.voting.model.AbstractEntity.START_SEQ;

public class RestaurantData {

    public static final MatcherFactory<Restaurant> REST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("menu");
    public static final MatcherFactory<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurant");

    private static final int RESTAURANT_ID = START_SEQ;
    private static final int DISH1_ID = START_SEQ + 2;

    public static final Restaurant restaurantWithTodayMenu = new Restaurant(RESTAURANT_ID, "RestaurantName1", "address1", List.of(
            new Dish(DISH1_ID + 2, "today's dish_1 from rest_1", 432, LocalDate.now(), null),
            new Dish(DISH1_ID + 4, "today's dish_2 from rest_1", 1000, LocalDate.now(), null)));

    public static final Restaurant restaurantWithAllMenu = new Restaurant(RESTAURANT_ID + 1, "RestaurantName2", "address2", List.of(
            new Dish(DISH1_ID + 5, "before yesterday's dish_1 from rest_2", 250, LocalDate.now().minus(2, ChronoUnit.DAYS), null),
            new Dish(DISH1_ID + 6, "before yesterday's dish_2 from rest_2", 500, LocalDate.now().minus(2, ChronoUnit.DAYS), null),
            new Dish(DISH1_ID + 7, "today's dish_1 from rest_2", 543, LocalDate.now(), null),
            new Dish(DISH1_ID + 8, "today's dish_2 from rest_2", 10, LocalDate.now(), null)));

    public static final List<Restaurant> allWithTodayMenu = List.of(
            restaurantWithTodayMenu,
            new Restaurant(RESTAURANT_ID + 1, "RestaurantName2", "address2", List.of(
                    new Dish(DISH1_ID + 7, "today's dish_1 from rest_2", 543, LocalDate.now(), null),
                    new Dish(DISH1_ID + 8, "today's dish_2 from rest_2", 10, LocalDate.now(), null))));
}
