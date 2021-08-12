package ru.graduation.voting.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.UserRepository;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;
import static ru.graduation.voting.web.SecurityUtil.authUserId;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractAdminController {

    private final UserRepository userRepository;

    public AdminRestaurantController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        int userId = authUserId();
        log.info("create restaurant by name: {} for user: {}", restaurant.getName(), userId);
        checkNew(restaurant);
        Restaurant created = save(restaurant, userId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody Restaurant restaurant) {
        int userId = authUserId();
        log.info("update restaurant id: {} for user: {}", id, userId);
        assureIdConsistent(restaurant, id);
        save(restaurant, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = authUserId();
        log.info("delete restaurant {} for user {}", id, userId);
        restaurantRepository.delete(id, userId);
    }

    Restaurant save(Restaurant restaurant, int userId) {
        if (!restaurant.isNew() && getRestaurant(restaurant.getId(), userId) == null) {
            return null;
        }
        restaurant.setOwner(userRepository.getById(userId));
        return restaurantRepository.save(restaurant);
    }
}