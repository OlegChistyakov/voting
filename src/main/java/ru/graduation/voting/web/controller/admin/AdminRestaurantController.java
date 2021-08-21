package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import javax.validation.Valid;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "/api/v1/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    static final String REST_URL = "api/v1/admin/restaurant";
    private final RestaurantRepository repository;
    private UniqueRestaurantNameValidator restaurantNameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantNameValidator);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create restaurant by name: {}", restaurant.getName());
        checkNew(restaurant);
        restaurant = repository.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @Valid @RequestBody Restaurant restaurant) {
        log.info("Update restaurant id: {}", id);
        assureIdConsistent(restaurant, id);
        repository.findExist(id);
        repository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant {}", id);
        repository.deleteExisted(id);
    }
}