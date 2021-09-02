package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {
    public static RestaurantTo convert(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
    }

    public static Restaurant convert(RestaurantTo to) {
        return new Restaurant(to.getId(), to.getName(), to.getAddress(), null);
    }
}