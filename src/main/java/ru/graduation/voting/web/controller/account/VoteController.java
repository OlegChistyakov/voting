package ru.graduation.voting.web.controller.account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.error.RequestNotBeExecutedException;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.repository.VoteRepository;
import ru.graduation.voting.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.graduation.voting.util.DateUtil.END_TIME_VOTE;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "api/v1/account/vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get vote history for user: {}", authUser.id());
        return voteRepository.getAll(authUser.id());
    }

    @GetMapping("/{id}")
    public Vote get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("Get vote by id: {} for user: {}", id, authUser.id());
        return voteRepository.findByIdAndUserId(id, authUser.id())
                .orElseThrow(() -> new NotFoundException("Vote by id:" + id + " not found"));
    }

    @PostMapping(value = "/{restId}")
    public ResponseEntity<Vote> toVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restId) {
        log.info("Vote for restaurant by id: {}", restId);

        Vote found = voteRepository.findByUserIdToday(authUser.id()).orElse(null);
        boolean possibleToVote = LocalTime.now().isBefore(END_TIME_VOTE);
        boolean alreadyVoted = found != null;

        if (possibleToVote) {
            Restaurant restaurant = restaurantRepository.findExist(restId);
            if (alreadyVoted) {
                found.setRestaurant(restaurant);
                return new ResponseEntity<>(voteRepository.save(found), HttpStatus.OK);
            } else {
                Vote vote = voteRepository.save(
                        new Vote(LocalDate.now(), authUser.getUser(), restaurant));
                return new ResponseEntity<>(vote, HttpStatus.OK);
            }
        } else {
            throw new RequestNotBeExecutedException("Voting ended");
        }
    }
}
