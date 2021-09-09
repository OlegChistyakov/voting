package ru.graduation.voting.web.controller.open;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.to.RestaurantTo;
import ru.graduation.voting.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.graduation.voting.util.ControllerUtil.formResponse;
import static ru.graduation.voting.util.DateUtil.*;
import static ru.graduation.voting.web.controller.open.RestaurantController.OPEN_REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = OPEN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    public static final String OPEN_REST_URL = "/api/v1/restaurants";
    private final RestaurantRepository restRepository;

    @GetMapping("/{restId}")
    public ResponseEntity<RestaurantTo> getById(@PathVariable Integer restId) {
        log.info("Get restaurant by id: {}", restId);
        return formResponse(restRepository.findById(restId), RestaurantTo.class, RestaurantUtil::convertToDTO);
    }

    @GetMapping("/{restId}/with-menu/by")
    public ResponseEntity<Restaurant> getByIdWithMenuBetweenDate(@PathVariable Integer restId,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = PATTERN) LocalDate startDate,
                                                                 @RequestParam @Nullable @DateTimeFormat(pattern = PATTERN) LocalDate endDate) {
        log.info("Get restaurant by id: {} with menu from start date: {} to end date: {}", restId, startDate, endDate);
        return formResponse(restRepository.getWithMenuBetweenDate(restId, startDayOrMin(startDate), endDayOrMax(endDate)));
    }

    /*Collection*/

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("Get all restaurants");
        return restRepository.findAll().stream()
                .map(RestaurantUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/with-menu/today")
    @Cacheable("restaurants")
    public List<Restaurant> getAllWithTodayMenu() {
        log.info("Get all restaurants with today menu");
        return restRepository.getAllWithMenuBetweenDate(LocalDate.now(), LocalDate.now());
    }

    @GetMapping("/with-menu/by")
    public List<Restaurant> getAllWithMenuBetweenDate(
            @RequestParam @Nullable @DateTimeFormat(pattern = PATTERN) LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = PATTERN) LocalDate endDate) {
        log.info("Get all restaurants with menu from start date: {} to end date: {}", startDate, endDate);
        return restRepository.getAllWithMenuBetweenDate(startDayOrMin(startDate), endDayOrMax(endDate));
    }
}