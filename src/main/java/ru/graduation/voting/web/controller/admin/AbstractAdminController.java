package ru.graduation.voting.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.repository.UserRepository;

public abstract class AbstractAdminController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DishRepository dishRepository;

    protected Restaurant getRestaurant(int id, int userId) {
        return restaurantRepository.findById(id)
                .filter(r -> r.getOwner().getId() == userId)
                .orElse(null);
    }
}
