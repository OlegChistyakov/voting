package ru.graduation.voting.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;
import static ru.graduation.voting.web.SecurityUtil.authUserId;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/admin/restaurant/{id}/dish", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractAdminController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@RequestBody Dish dish, @PathVariable int id) {
        int userId = authUserId();
        log.info("create dish for restaurant id: {} for user: {}", id, userId);
        checkNew(dish);
        Restaurant restaurant = getRestaurant(id, userId);
        if (restaurant == null) {
            return null;
        }
        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int dishId, @RequestBody Dish dish) {
        int userId = authUserId();
        log.info("update dish id: {} for user: {}", id, userId);
        assureIdConsistent(dish, dishId);
        Restaurant restaurant = getRestaurant(id, userId);
        if (restaurant == null) {
            return;
        }
        if (!restaurant.getMenu().contains(dish)) {
            return;
        }
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }
}