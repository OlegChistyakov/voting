package ru.graduation.voting.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.repository.UserRepository;

public abstract class AbstractAdminController {

    @Autowired
    private UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

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
