package ru.graduation.voting.web.controller.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.repository.VoteRepository;

import java.util.List;

import static ru.graduation.voting.web.SecurityUtil.authUserId;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/account/vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    final VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping()
    List<Vote> getAllVote() {
        int userId = authUserId();
        log.info("get voting history for user: {}", userId);
        return voteRepository.getAll(userId);
    }
}
