package ru.graduation.voting.web.controller.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Restaurant;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/admin/restaurant",  produces = MediaType.APPLICATION_JSON_VALUE)

public class AdminRestaurantController extends AbstractRestaurantController {

    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        Restaurant created = repository.save(restaurant);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}