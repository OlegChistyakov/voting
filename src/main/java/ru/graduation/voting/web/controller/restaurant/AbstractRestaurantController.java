package ru.graduation.voting.web.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.repository.RestaurantRepository;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    protected <T> ResponseEntity<T> response(Supplier<Optional<T>> found) {
        T t = found.get().orElseThrow(() -> new NotFoundException("Restaurant not found or there is no menu for this period"));
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}
