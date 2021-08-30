package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.web.controller.config.ClockDisableVote;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_VOTE_CLOSE;
import static ru.graduation.voting.web.controller.UserTestData.*;
import static ru.graduation.voting.web.controller.account.AccountData.MATCHER;
import static ru.graduation.voting.web.controller.account.AccountData.getFirstVote;
import static ru.graduation.voting.web.controller.account.AccountVoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST1_ID;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST2_ID;

@SpringBootTest(classes = ClockDisableVote.class)
public class AfterVoteCloseControllerTest extends AbstractVoteControllerTest {

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
        perform(MockMvcRequestBuilders.post(URL + REST2_ID))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(containsString(EXCEPTION_VOTE_CLOSE)));
    }
}