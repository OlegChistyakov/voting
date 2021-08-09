package ru.graduation.voting.web.controller.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import java.time.LocalDate;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;

@RestController
@RequestMapping(value = "api/v1/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{restId}")
    public Restaurant getById(@PathVariable Integer restId) {
        log.info("getting restaurant by id: {}", restId);
        return repository.findById(restId).orElse(null);
    }

    @GetMapping("/{restId}/with-menu")
    public Restaurant getByIdWithMenu(@PathVariable Integer restId) {
        log.info("getting restaurant by id: {} with menu history", restId);
        return repository.getWithMenu(restId);
    }

    @GetMapping("/{restId}/with-menu/by")
    public Restaurant getByIdWithMenuBetweenDate(@PathVariable Integer restId,
                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("getting restaurant by id: {} with menu from start date: {} to end date: {}", restId, startDate, endDate);
        return repository.getWithMenuBetweenDate(restId, startDayOrMin(startDate), endDayOrMax(endDate));
    }
}