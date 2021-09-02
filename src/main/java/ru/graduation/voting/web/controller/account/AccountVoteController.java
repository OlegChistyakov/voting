package ru.graduation.voting.web.controller.account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.error.RequestNotBeExecutedException;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.model.User;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.repository.VoteRepository;
import ru.graduation.voting.to.VoteTo;
import ru.graduation.voting.util.VoteUtil;
import ru.graduation.voting.web.AuthUser;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.graduation.voting.util.DateUtil.END_TIME_VOTE;
import static ru.graduation.voting.web.GlobalExceptionHandler.*;
import static ru.graduation.voting.web.controller.account.AccountVoteController.VOTE_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountVoteController {

    public static final String VOTE_URL = "/api/v1/account/vote";
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private Clock clock;

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get vote history for user: {}", authUser.id());
        return VoteUtil.convertList(voteRepository.getAllByUserId(authUser.id()));
    }

    @GetMapping("/{id}")
    public VoteTo get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("Get vote by id: {} for user: {}", id, authUser.id());
        Vote found = voteRepository.findByIdAndUserId(id, authUser.id())
                .orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
        return VoteUtil.convert(found);
    }

    @PostMapping(value = "/{restId}")
    @Transactional
    public ResponseEntity<VoteTo> toVote(@AuthenticationPrincipal AuthUser authUser,
                                         @PathVariable int restId) {
        log.info("Vote for restaurant by id: {}", restId);

        Restaurant foundRestaurant = restaurantRepository.findExist(restId);
        Vote foundVote = voteRepository.findByUserIdToday(authUser.id()).orElse(null);

        boolean isVotingActive = LocalTime.now(clock).isBefore(END_TIME_VOTE);
        boolean alreadyVoted = foundVote != null;

        if (isVotingActive) {
            if (alreadyVoted) {
                checkRepeatVoice(restId, foundVote.getRestaurant());
                foundVote.setRestaurant(foundRestaurant);
                return new ResponseEntity<>(VoteUtil.convert(voteRepository.save(foundVote)), HttpStatus.OK);
            } else {
                return create(authUser.getUser(), foundRestaurant);
            }
        } else {
            if (alreadyVoted) {
                throw new RequestNotBeExecutedException(EXCEPTION_VOTE_CLOSE);
            } else {
                return create(authUser.getUser(), foundRestaurant);
            }
        }
    }

    private void checkRepeatVoice(int restId, Restaurant found) {
        if (found.id() == restId) {
            throw new RequestNotBeExecutedException(EXCEPTION_REPEAT_REQUEST);
        }
    }

    private ResponseEntity<VoteTo> create(User user, Restaurant restaurant) {
        Vote vote = voteRepository.save(new Vote(LocalDate.now(), user, restaurant));
        return new ResponseEntity<>(VoteUtil.convert(vote), HttpStatus.OK);
    }
}