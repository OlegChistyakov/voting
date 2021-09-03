package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.to.DishTo;

@UtilityClass
public class DishUtil {
    public static Dish convertToModel(DishTo to, Restaurant restaurant) {
        return new Dish(to.getId(), to.getDescription(), to.getPrice(), to.getDate(), restaurant);
    }

    public static DishTo convertToDTO(Dish dish) {
        return new DishTo(dish.getId(), dish.getDescription(), dish.getPrice(), dish.getDate());
    }
}