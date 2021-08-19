package ru.graduation.voting.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.repository.RestaurantRepository;

public abstract class AbstractAdminController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishRepository dishRepository;

    protected Restaurant getRefRestaurant(int id) {
        return restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("The restaurant by id: " + id + " not exists"));
    }
}
