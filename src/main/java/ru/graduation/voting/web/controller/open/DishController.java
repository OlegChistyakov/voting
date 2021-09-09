package ru.graduation.voting.web.controller.open;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.to.DishTo;
import ru.graduation.voting.util.DishUtil;

import java.util.List;

import static ru.graduation.voting.util.ControllerUtil.formResponse;
import static ru.graduation.voting.web.controller.open.DishController.OPEN_DISH_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = OPEN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    public static final String OPEN_DISH_URL = "/api/v1/dishes";
    private final DishRepository dishRepository;

    @GetMapping("/{id}")
    public ResponseEntity<DishTo> get(@PathVariable int id) {
        log.info("Get dish by id: {}", id);
        return formResponse(dishRepository.findById(id), DishTo.class, DishUtil::convertToDTO);
    }

    @GetMapping("/by")
    public List<Dish> getByName(@RequestParam String description) {
        return dishRepository.findByDescriptionIgnoreCase(description);
    }
}