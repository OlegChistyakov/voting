package ru.graduation.voting.web.controller.open;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.to.DishTo;
import ru.graduation.voting.to.RestaurantTo;
import ru.graduation.voting.util.DishUtil;
import ru.graduation.voting.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.controller.open.RestaurantController.OPEN_REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = OPEN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    public static final String OPEN_REST_URL = "/api/v1/restaurants";
    private final RestaurantRepository repository;

    @GetMapping("/{restId}")
    public RestaurantTo getById(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {}", restId);
        Restaurant found = repository.findById(restId).orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
        return RestaurantUtil.convertToDTO(found);
    }

    @GetMapping("/{restId}/dish/{dishId}")
    public ResponseEntity<DishTo> getWithDish(@PathVariable int restId, @PathVariable int dishId) {
        log.info("Get restaurant by id: {} with dish id: {}", restId, dishId);
        Dish dish = repository
                .getWithDish(restId, dishId)
                .orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY))
                .getMenu().get(0);
        return new ResponseEntity<>(DishUtil.convertToDTO(dish), HttpStatus.OK);
    }

    @GetMapping("/{restId}/with-menu")
    public ResponseEntity<Restaurant> getByIdWithMenu(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {} with menu history", restId);
        return response(() -> repository.getWithMenu(restId));
    }

    @GetMapping("/{restId}/with-menu/by")
    public ResponseEntity<Restaurant> getByIdWithMenuBetweenDate(@PathVariable Integer restId,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("Get restaurant by id: {} with menu from start date: {} to end date: {}", restId, startDate, endDate);
        return response(() -> repository.getWithMenuBetweenDate(restId, startDayOrMin(startDate), endDayOrMax(endDate)));
    }

    /*Collection*/

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("Get all restaurants");
        return repository.findAll().stream()
                .map(RestaurantUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/with-menu/today")
    @Cacheable("restaurants")
    public ResponseEntity<List<Restaurant>> getAllWithTodayMenu() {
        log.info("Get all restaurants with today menu");
        return response(() -> repository.getAllWithMenuBetweenDate(LocalDate.now(), LocalDate.now()));
    }

    @GetMapping("/with-menu/by")
    public ResponseEntity<List<Restaurant>> getAllWithMenuBetweenDate(
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("Get all restaurants with menu from start date: {} to end date: {}", startDate, endDate);
        return response(() ->
                repository.getAllWithMenuBetweenDate(startDayOrMin(startDate), endDayOrMax(endDate)));
    }

    private <T> ResponseEntity<T> response(Supplier<Optional<T>> found) {
        T t = found.get().orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}