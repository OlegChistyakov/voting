package ru.graduation.voting.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import javax.validation.Valid;

import static ru.graduation.voting.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.voting.util.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "api/v1/admin/restaurant/{id}/dish", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractAdminController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("Create dish for restaurant id: {}", id);
        checkNew(dish);
        Restaurant restaurant = getRefRestaurant(id);
        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int dishId, @RequestBody Dish dish) {
        log.info("Update dish id: {} for restaurant: {}", dishId, id);
        assureIdConsistent(dish, dishId);
        Dish saveDish = dishRepository
                .findByIdWithRestaurant(dishId, id)
                .orElseThrow(() -> new NotFoundException("The dish by id: " + dishId + " in restaurant by id: " + id + " not exists"));
        dish.setRestaurant(saveDish.getRestaurant());
        dishRepository.save(dish);
    }

    @DeleteMapping(value = "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int dishId) {
        log.info("Delete dish id: {} from restaurant: {}", dishId, id);
        dishRepository.deleteExisted(dishId, id);
    }
}