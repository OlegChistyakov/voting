package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Restaurant;

import javax.validation.Valid;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "api/v1/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractAdminController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create restaurant by name: {}", restaurant.getName());
        checkNew(restaurant);
        restaurant = restaurantRepository.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @Valid @RequestBody Restaurant restaurant) {
        log.info("Update restaurant id: {}", id);
        assureIdConsistent(restaurant, id);
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.save(restaurant);
        } else {
            throw new NotFoundException("The restaurant by id: " + id + " not exists");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }
}