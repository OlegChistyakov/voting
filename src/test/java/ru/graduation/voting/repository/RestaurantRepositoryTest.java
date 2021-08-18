package ru.graduation.voting.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation.voting.model.Restaurant;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ObjectMapper mapper;
//    @Test
//    void create() {
//        Restaurant saveRestaurant = repository.save(getNew());
//        Restaurant newRestaurant = getNew();
//
//        int newId = saveRestaurant.getId();
//        newRestaurant.setId(newId);
//
//        for (int i = 0; i < saveRestaurant.getMenu().size(); i++) {
//            newRestaurant.getMenu().get(i).setId(saveRestaurant.getMenu().get(i).getId());
//        }
//
//        REST_MATCHER.assertMatch(saveRestaurant, newRestaurant);
//        DISH_MATCHER.assertMatch(saveRestaurant.getMenu(), newRestaurant.getMenu());
//    }
//
//    @Test
//    void getWithTodayMenu() {
//        Restaurant actual = repository.etWithMenuByDate(100_000, LocalDate.now());
//        REST_MATCHER.assertMatch(actual, restaurantWithTodayMenu);
//        DISH_MATCHER.assertMatch(actual.getMenu(), restaurantWithTodayMenu.getMenu());
//    }
//
////    @Test
////    void getWithAllMenu() {
////        Restaurant actual = repository.getWithAllMenuBetweenDate(LocalDate.now().minus(2, ChronoUnit.DAYS), LocalDate.now());
////        REST_MATCHER.assertMatch(actual, restaurantWithAllMenu);
////        DISH_MATCHER.assertMatch(actual.getMenu(), restaurantWithAllMenu.getMenu());
////    }
//
//    @Test
//    void getAllWithMenuByDate() {
//        List<Restaurant> actual = repository.getAllWithMenuByDate(LocalDate.now());
//        REST_MATCHER.assertMatch(actual, allWithTodayMenu);
//        DISH_MATCHER.assertMatch(actual.get(0).getMenu(), allWithTodayMenu.get(0).getMenu());
//        DISH_MATCHER.assertMatch(actual.get(1).getMenu(), allWithTodayMenu.get(1).getMenu());
//    }

    @Test
    void create() throws JsonProcessingException {
        Restaurant restaurant = restRepo.getByNameIgnoreCase("restaurantNAME1").get();
        restaurant.getId();
    }
}