package ru.graduation.voting.web.controller.open;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.repository.DishRepository;

import static ru.graduation.voting.web.controller.open.DishController.OPEN_DISH_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = OPEN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    public static final String OPEN_DISH_URL = "/api/v1/dish";
    private final DishRepository dishRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        return new ResponseEntity<>(dishRepository.findExist(id), HttpStatus.OK);
    }
}