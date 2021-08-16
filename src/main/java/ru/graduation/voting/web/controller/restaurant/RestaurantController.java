package ru.graduation.voting.web.controller.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    @GetMapping("/{restId}")
    public ResponseEntity<Restaurant> getById(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {}", restId);
        Restaurant found = restaurantRepository
                .findById(restId)
                .orElseThrow(() -> new NotFoundException("Restaurant by id: " + restId + " not found"));
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/{restId}/with-menu")
    public Restaurant getByIdWithMenu(@PathVariable Integer restId) {
        log.info("getting restaurant by id: {} with menu history", restId);
        return restaurantRepository.getWithMenu(restId);
    }

    @GetMapping("/{restId}/with-menu/by")
    public Restaurant getByIdWithMenuBetweenDate(@PathVariable Integer restId,
                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("getting restaurant by id: {} with menu from start date: {} to end date: {}", restId, startDate, endDate);
        return restaurantRepository.getWithMenuBetweenDate(restId, startDayOrMin(startDate), endDayOrMax(endDate));
    }
}