package ru.graduation.voting.web.controller.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class CollectionOfRestaurantsController extends AbstractRestaurantController {

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getting all restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/with-today-menu")
    @Cacheable("restaurants")
    public List<Restaurant> getAllWithTodayMenu() {
        log.info("getting all restaurants with today menu");
        return restaurantRepository.getAllWithMenuBetweenDate(LocalDate.now(), LocalDate.now());
    }

    @GetMapping("/with-menu")
    public List<Restaurant> getAllWithMenuBetweenDate(
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("getting all restaurants with menu from start date: {} to end date: {}", startDate, endDate);
        return restaurantRepository.getAllWithMenuBetweenDate(startDayOrMin(startDate), endDayOrMax(endDate));
    }
}