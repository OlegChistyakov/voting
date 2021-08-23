package ru.graduation.voting.web.controller.open;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();});

    public static final int REST1_ID = 100_003;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "RestaurantName1", "address1", null);
    public static final Restaurant rest2 = new Restaurant(100_004, "RestaurantName2", "address2", null);

    public static final Dish dish1 = new Dish(100_006, "yesterday's dish_1 from rest_1", 546, LocalDate.now(), rest1);
    public static final Dish dish2 = new Dish(100_007, "today's dish_1 from rest_1", 432, LocalDate.now(), rest1);
    public static final Dish dish3 = new Dish(100_010, "before yesterday's dish_1 from rest_2", 250, LocalDate.now(), rest2);
    public static final Dish dish4 = new Dish(100_012, "today's dish_1 from rest_2", 543, LocalDate.now(), rest2);

    static {
        rest1.setMenu(List.of(dish1, dish2));
        rest2.setMenu(List.of(dish3, dish4));
    }
}