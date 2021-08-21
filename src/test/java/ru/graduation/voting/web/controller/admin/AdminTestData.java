package ru.graduation.voting.web.controller.admin;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Restaurant;

public class AdminTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static Restaurant getNewRestaurant() {
        return new Restaurant("New name", "New address", null);
    }
}
