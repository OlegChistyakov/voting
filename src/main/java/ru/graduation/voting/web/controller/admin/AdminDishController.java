package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.controller.admin.AdminDishController.ADMIN_DISH_URL;
import static ru.graduation.voting.web.controller.open.RestaurantController.OPEN_REST_URL;


@RestController
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = ADMIN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String ADMIN_DISH_URL = "/api/v1/admin/restaurants/{id}/dish";
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("Create dish for restaurant id: {}", id);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.findExist(id);
        dish.setRestaurant(restaurant);
        dish = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(OPEN_REST_URL + "{restId}/dish/{dishId}")
                .buildAndExpand(restaurant.getId(), dish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(dish);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int dishId, @RequestBody Dish dish) {
        log.info("Update dish id: {} for restaurant: {}", dishId, id);
        assureIdConsistent(dish, dishId);
        Dish saveDish = dishRepository
                .findByIdWithRestaurant(dishId, id)
                .orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
        dish.setRestaurant(saveDish.getRestaurant());
        dishRepository.save(dish);
    }

    @DeleteMapping(value = "/{dishId}")
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int dishId) {
        log.info("Delete dish id: {} from restaurant: {}", dishId, id);
        dishRepository.deleteExistedByRestId(dishId, id);
    }
}