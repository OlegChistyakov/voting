package ru.graduation.voting.web.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.repository.RestaurantRepository;

public abstract class AbstractRestaurantController {
    @Autowired
    protected RestaurantRepository repository;
}
