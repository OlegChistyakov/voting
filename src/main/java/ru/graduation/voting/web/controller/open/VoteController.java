package ru.graduation.voting.web.controller.open;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.repository.VoteRepository;
import ru.graduation.voting.to.VoteTo;
import ru.graduation.voting.util.VoteUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.util.DateUtil.endDayOrMax;
import static ru.graduation.voting.util.DateUtil.startDayOrMin;
import static ru.graduation.voting.web.controller.open.VoteController.OPEN_VOTE_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = OPEN_VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String OPEN_VOTE_URL = "/api/v1/votes";
    private final VoteRepository repository;

    @GetMapping
    public List<VoteTo> getAll() {
        return VoteUtil.convertToListDTO(repository.findAll());
    }

    @GetMapping("/today")
    @Cacheable("votes")
    public List<VoteTo> getAllToday() {
        return VoteUtil.convertToListDTO(repository.getAllByBetweenDate(LocalDate.now(), LocalDate.now()));
    }

    @GetMapping("/by")
    public List<VoteTo> getAllBetweenDate(@RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                          @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return VoteUtil.convertToListDTO(repository.getAllByBetweenDate(startDayOrMin(startDate), endDayOrMax(endDate)));
    }
}