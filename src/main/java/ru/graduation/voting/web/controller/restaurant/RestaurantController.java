package ru.graduation.voting.web.controller.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    @GetMapping("/{restId}")
    public ResponseEntity<Restaurant> getById(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {}", restId);
        return response(() -> restaurantRepository.findById(restId));
    }

    @GetMapping("/{restId}/with-menu")
    public ResponseEntity<Restaurant> getByIdWithMenu(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {} with menu history", restId);
        return response(() -> restaurantRepository.getWithMenu(restId));
    }

    @GetMapping("/{restId}/with-menu/by")
    public ResponseEntity<Restaurant> getByIdWithMenuBetweenDate(@PathVariable Integer restId,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("Get restaurant by id: {} with menu from start date: {} to end date: {}", restId, startDate, endDate);
        return response(() -> restaurantRepository.getWithMenuBetweenDate(restId, startDate, endDate));
    }
}