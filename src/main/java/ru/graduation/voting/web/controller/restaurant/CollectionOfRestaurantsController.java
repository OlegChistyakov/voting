package ru.graduation.voting.web.controller.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;

@RestController
@RequestMapping(value = "api/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class CollectionOfRestaurantsController {

    private final RestaurantRepository repository;

    public CollectionOfRestaurantsController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @GetMapping("/with-today-menu")
    public List<Restaurant> getAllWithTodayMenu() {
        return repository.getAllWithMenuBetweenDate(LocalDate.now(), LocalDate.now());
    }

    @GetMapping("/with-menu")
    public List<Restaurant> getAllWithMenuBetweenDate(
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return repository.getAllWithMenuBetweenDate(startDayOrMin(startDate), endDayOrMax(endDate));
    }
}