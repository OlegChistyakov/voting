package ru.graduation.voting.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {

    private final RestaurantRepository repository;

    public ProfileRestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("restaurants/")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @GetMapping("restaurants/today")
    public List<Restaurant> getAllTodayWithMenu() {
        return repository.getAllWithMenuByDate(LocalDate.now());
    }

    @GetMapping("restaurants/filter")
    public List<Restaurant> getAllBetweenDate(
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return repository.getWithAllMenuBetweenDate(
                DateUtil.atStartOfDayOrMin(startDate),
                DateUtil.atStartOfNextDayOrMax(endDate));
    }

    @GetMapping("restaurant/{restId}")
    public Restaurant getById(@PathVariable Integer restId) {
        return repository.get(restId);
    }

    @GetMapping("restaurant/{restId}/with-menu")
    public Restaurant getByIdWithMenu(@PathVariable Integer restId) {
        return repository.getWithMenu(restId);
    }
}
