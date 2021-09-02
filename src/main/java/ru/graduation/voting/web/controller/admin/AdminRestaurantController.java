package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.to.RestaurantTo;
import ru.graduation.voting.util.RestaurantUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;
import static ru.graduation.voting.web.controller.admin.AdminRestaurantController.ADMIN_REST_URL;
import static ru.graduation.voting.web.controller.open.RestaurantController.OPEN_REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String ADMIN_REST_URL = "/api/v1/admin/restaurant";
    private final RestaurantRepository repository;
    private final UniqueRestaurantNameValidator restaurantNameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantNameValidator);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<RestaurantTo> createWithLocation(@Valid @RequestBody RestaurantTo to) {
        log.info("Create restaurant by name: {}", to.getName());
        checkNew(to);
        Restaurant restaurant = repository.save(RestaurantUtil.convertToModel(to));
        to.setId(restaurant.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(OPEN_REST_URL + "/{id}")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(to);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @Valid @RequestBody RestaurantTo to) {
        log.info("Update restaurant id: {}", id);
        assureIdConsistent(to, id);
        repository.findExist(id);
        repository.save(RestaurantUtil.convertToModel(to));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant {}", id);
        repository.deleteExisted(id);
    }
}