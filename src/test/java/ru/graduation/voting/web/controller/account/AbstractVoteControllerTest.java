package ru.graduation.voting.web.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.AbstractControllerTest;
import ru.graduation.voting.repository.VoteRepository;

import static ru.graduation.voting.web.controller.account.VoteController.VOTE_URL;

public abstract class AbstractVoteControllerTest extends AbstractControllerTest {
    protected final String URL = VOTE_URL + "/";

    @Autowired
    VoteRepository repository;
}
