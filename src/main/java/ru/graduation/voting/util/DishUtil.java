package ru.graduation.voting.util;

import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.to.DishTo;

public class DishUtil {

    public static Dish convertFromTo(DishTo to, Restaurant restaurant) {
        return new Dish(to.getId(), to.getDescription(), to.getPrice(), to.getDate(), restaurant);
    }

    private DishUtil() {
    }
}