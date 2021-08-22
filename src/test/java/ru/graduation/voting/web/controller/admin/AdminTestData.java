package ru.graduation.voting.web.controller.admin;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;

public class AdminTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant.menu");

    public static final int NON_EXIST_ENTITY_ID = 100_100;
    public static final int EXIST_REST_ID = 100_003;
    public static final int EXIST_MEAL_ID = 100_007;
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final Restaurant exist_rest = new Restaurant(EXIST_REST_ID, "RestaurantName1", "address1", null);
    public static final Dish exist_dish = new Dish(EXIST_MEAL_ID, "today's dish_1 from rest_1", 432, LocalDate.now(), exist_rest);

    public static Restaurant getNewRestaurant() {
        return new Restaurant("New name", "New address", null);
    }

    public static Restaurant getUpdateRestaurant() {
        return new Restaurant(EXIST_REST_ID, "Update name", "Update address", null);
    }

    public static Dish getNewDish() {
        return new Dish("New dish", 500, LocalDate.now(), exist_rest);
    }

    public static Dish getUpdateDish() {
        return new Dish(EXIST_MEAL_ID, "Update description", 100, LocalDate.now(), exist_rest);
    }
}