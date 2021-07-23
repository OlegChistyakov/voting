package ru.graduation.voting.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "rest/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = repository.getAllWithMenuByDate(LocalDate.now());
        return restaurants;
    }
}
