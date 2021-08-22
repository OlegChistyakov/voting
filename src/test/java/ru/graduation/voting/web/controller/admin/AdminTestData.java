package ru.graduation.voting.web.controller.admin;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Restaurant;

public class AdminTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int NON_EXIST_REST_ID = 100_000;
    public static final int REST_ID = 100_003;
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static Restaurant getNew() {
        return new Restaurant("New name", "New address", null);
    }

    public static Restaurant getUpdate() {
        return new Restaurant(REST_ID, "Update name", "Update address", null);
    }

    public static Restaurant getDuplicate() {
        return new Restaurant(REST_ID, "RestaurantName1", "address1", null);
    }
}
