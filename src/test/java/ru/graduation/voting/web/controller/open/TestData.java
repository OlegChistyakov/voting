package ru.graduation.voting.web.controller.open;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {

    public static final LocalDate today = LocalDate.now();
    public static final LocalDate yesterday = LocalDate.now().minusDays(1);

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("menu.restaurant").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("menu.restaurant").isEqualTo(e));

    public static final int REST1_ID = 100_003;
    public static final int REST2_ID = 100_004;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "RestaurantName1", "address1", null);
    public static final Restaurant rest2 = new Restaurant(REST2_ID, "RestaurantName2", "address2", null);
    public static final Restaurant rest3 = new Restaurant(100_005, "RestaurantName3", "address3", null);

    public static final Dish dish1 = new Dish(100_006, "yesterday's dish_1 from rest_1", 546, yesterday, rest1);
    public static final Dish dish2 = new Dish(100_007, "today's dish_1 from rest_1", 432, today, rest1);
    public static final Dish dish3 = new Dish(100_008, "yesterday's dish_2 from rest_1", 100, yesterday, rest1);
    public static final Dish dish4 = new Dish(100_009, "today's dish_2 from rest_1", 1000, today, rest1);
    public static final Dish dish5 = new Dish(100_010, "before yesterday's dish_1 from rest_2", 250, LocalDate.now().minusDays(2), rest2);
    public static final Dish dish6 = new Dish(100_012, "today's dish_1 from rest_2", 543, today, rest2);
    public static final Dish dish7 = new Dish(100_013, "today's dish_2 from rest_2", 10, today, rest2);

    private static final List<Dish> allDishes = List.of(dish1, dish2, dish3, dish4, dish5, dish6, dish7);
    public static final List<Restaurant> allRestaurants = List.of(rest1, rest2, rest3);

    public static List<Dish> getMenuByDate(int id, LocalDate date) {
        return allDishes.stream()
                .filter(d -> d.getDate().equals(date) && d.getRestaurant().id() == id)
                .collect(Collectors.toList());
    }

    public static List<Dish> getMenu(int id) {
        return allDishes.stream()
                .filter(d -> d.getRestaurant().id() == id)
                .collect(Collectors.toList());
    }
}