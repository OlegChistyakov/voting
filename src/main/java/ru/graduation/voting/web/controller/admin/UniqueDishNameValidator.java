package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.to.DishTo;
import ru.graduation.voting.web.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;

@Component
@AllArgsConstructor
public class UniqueDishNameValidator implements org.springframework.validation.Validator {
    private static final int CELL_REST_ID = 5;
    private static final int CELL_DISH_ID = 7;

    private final RestaurantRepository restRepository;
    private final DishRepository dishRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return DishTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] splitURI = request.getRequestURI().split("/");
        int restId = Integer.parseInt(splitURI[CELL_REST_ID]);
        DishTo to = (DishTo) target;

        Restaurant restaurant = restRepository.getWithMenuBetweenDate(restId, to.getDate(), to.getDate()).orElse(null);
        if (restaurant == null) {
            return;
        }

        Dish found = restaurant.getMenu().stream()
                .filter(d -> d.getDescription().equalsIgnoreCase(to.getDescription()))
                .findFirst().orElse(null);
        if (found == null) {
            return;
        }

        if (request.getMethod().equals("PUT")) {
            int dishId = Integer.parseInt(splitURI[CELL_DISH_ID]);
            if (found.id() == dishId) {
                return;
            } else {
                if (!dishRepository.existsById(dishId)) {
                    throw new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY);
                }
            }
        }

        errors.rejectValue("description", null, GlobalExceptionHandler.EXCEPTION_DUPLICATE_DISH_NAME);
    }
}