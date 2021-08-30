package ru.graduation.voting.web.controller;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Role;
import ru.graduation.voting.model.User;

import java.util.Collections;
import java.util.Date;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID1 = 100_000;
    public static final int USER_ID2 = 100_001;
    public static final int ADMIN_ID = 100_002;
    public static final int NOT_FOUND = 0;

    public static final String USER_MAIL1 = "user@gmail.com";
    public static final String USER_MAIL2 = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user1 = new User(USER_ID1, "User#1", USER_MAIL1, "{noop}password", Role.USER);
    public static final User user2 = new User(USER_ID2, "User#2", USER_MAIL2, "{noop}password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "{noop}admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_ID1, "UpdatedName", USER_MAIL1, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }
}
