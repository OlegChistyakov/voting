package ru.graduation.voting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;

import static ru.graduation.voting.repository.RestaurantData.*;

@SpringBootTest
@Sql(scripts = "classpath:db/popDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository repository;

    @Test
    void getWithTodayMenu() {
        Restaurant actual = repository.getWithMenuByDate(100_000, LocalDate.now());
        REST_MATCHER.assertMatch(actual, restaurantWithTodayMenu);
        DISH_MATCHER.assertMatch(actual.getMenu(), restaurantWithTodayMenu.getMenu());
    }
}