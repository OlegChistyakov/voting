package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.to.RestaurantTo;
import ru.graduation.voting.web.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueRestaurantNameValidator implements org.springframework.validation.Validator {

    private final RestaurantRepository repository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return RestaurantTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RestaurantTo validRestaurant = (RestaurantTo) target;
        Restaurant foundRestaurant = repository
                .getByNameIgnoreCase(validRestaurant.getName())
                .orElse(null);

        if (foundRestaurant == null) {
            return;
        }

        if (request.getMethod().equals("PUT")) {
            int idValidRestaurant = request
                    .getRequestURI()
                    .transform(s -> Integer.parseInt(s.substring(s.lastIndexOf("/") + 1)));
            if (idValidRestaurant == foundRestaurant.id()) {
                return;
            }
        }

        errors.rejectValue("name", null, GlobalExceptionHandler.EXCEPTION_RESTAURANT_DUPLICATE_NAME);
    }
}