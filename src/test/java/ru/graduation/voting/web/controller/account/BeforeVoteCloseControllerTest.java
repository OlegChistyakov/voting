package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.web.controller.config.ClockEnableVote;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.controller.account.AccountData.*;
import static ru.graduation.voting.web.controller.account.VoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST1_ID;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST2_ID;
import static ru.graduation.voting.web.controller.user.UserTestData.*;

@SpringBootTest(classes = ClockEnableVote.class)
public class BeforeVoteCloseControllerTest extends AbstractVoteControllerTest {

    private static final String URL = VOTE_URL + "/";

    @Test
    @WithUserDetails(USER_MAIL1)
    void firstVote() throws Exception {
        Vote firstVote = getFirstVote();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL + REST1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(firstVote));

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        MATCHER.assertMatch(created, firstVote);
        firstVote.setUser(user1);
        MATCHER.assertMatch(repository.getById(newId), firstVote);
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void alreadyVote() throws Exception {
        Vote reVoting = getReVoting();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL + REST2_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(reVoting));

        Vote updated = MATCHER.readFromJson(action);
        int newId = updated.id();
        MATCHER.assertMatch(updated, reVoting);
        reVoting.setUser(user2);
        MATCHER.assertMatch(repository.getById(newId), reVoting);
    }

    @Test
    @WithUserDetails(USER_MAIL1)
    void getNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + vote4.id()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }
}
