package ru.graduation.voting.web.controller.account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.voting.error.NotFoundException;
import ru.graduation.voting.error.RequestNotBeExecutedException;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.repository.RestaurantRepository;
import ru.graduation.voting.repository.VoteRepository;
import ru.graduation.voting.to.IntegerTo;
import ru.graduation.voting.to.VoteTo;
import ru.graduation.voting.util.VoteUtil;
import ru.graduation.voting.web.AuthUser;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.graduation.voting.util.ControllerUtil.formResponse;
import static ru.graduation.voting.util.ControllerUtil.unpack;
import static ru.graduation.voting.util.DateUtil.END_TIME_VOTE;
import static ru.graduation.voting.web.GlobalExceptionHandler.*;
import static ru.graduation.voting.web.controller.account.AccountVoteController.VOTE_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountVoteController {

    public static final String VOTE_URL = "/api/v1/account/votes";
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final Clock clock;

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get vote history for user: {}", authUser.id());
        return VoteUtil.convertToListDTO(voteRepository.getAllByUserId(authUser.id()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("Get vote by id: {} for user: {}", id, authUser.id());
        Optional<Vote> optional = voteRepository.findByIdAndUserId(id, authUser.id());
        return formResponse(optional, VoteTo.class, VoteUtil::convertToDTO);
    }

    @GetMapping("/today")
    public ResponseEntity<VoteTo> getToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get today vote for user: {}", authUser.id());
        Optional<Vote> optional = voteRepository.findByUserIdAndLocalDate(authUser.id(), LocalDate.now());
        return formResponse(optional, VoteTo.class, VoteUtil::convertToDTO);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VoteTo> createVote(@AuthenticationPrincipal AuthUser authUser, @RequestBody IntegerTo to) {
        int restId = getId(to);
        log.info("Create vote for restaurant by id: {}", restId);

        Restaurant foundRestaurant = restaurantRepository.findExist(restId);
        Vote foundVote = voteRepository.findByUserIdAndLocalDate(authUser.id(), LocalDate.now()).orElse(null);
        boolean alreadyVoted = foundVote != null;

        if (alreadyVoted) {
            throw new NotFoundException(EXCEPTION_REPEAT_POST_REQUEST);
        } else {
            Vote vote = voteRepository.save(new Vote(LocalDate.now(), authUser.getUser(), foundRestaurant));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(VOTE_URL + "/" + vote.id()).build().toUri();
            return ResponseEntity.created(uriOfNewResource).body(VoteUtil.convertToDTO(vote));
        }
    }

    @PutMapping
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@AuthenticationPrincipal AuthUser authUser, @RequestBody IntegerTo to) {
        int restId = getId(to);
        log.info("Update vote for restaurant by id: {}", restId);

        Restaurant foundRestaurant = restaurantRepository.findExist(restId);
        Vote foundVote = unpack(voteRepository.findByUserIdAndLocalDate(authUser.id(), LocalDate.now()));
        boolean isVotingActive = LocalTime.now(clock).isBefore(END_TIME_VOTE);

        if (isVotingActive) {
            checkRepeatVoice(restId, foundVote.getRestaurant());
            foundVote.setRestaurant(foundRestaurant);
        } else {
            throw new RequestNotBeExecutedException(EXCEPTION_VOTE_CLOSE);
        }
    }

    private void checkRepeatVoice(int restId, Restaurant found) {
        if (found.id() == restId) {
            throw new RequestNotBeExecutedException(EXCEPTION_REPEAT_REQUEST);
        }
    }

    private int getId(IntegerTo to) {
        if (to.getId() == null) {
            throw new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY);
        }
        return to.getId();
    }
}