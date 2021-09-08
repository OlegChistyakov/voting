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
import static ru.graduation.voting.util.JsonUtil.writeValue;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_VOTE_CLOSE;
import static ru.graduation.voting.web.controller.UserTestData.*;
import static ru.graduation.voting.web.controller.account.AccountData.MATCHER;
import static ru.graduation.voting.web.controller.account.AccountData.getFirstVote;
import static ru.graduation.voting.web.controller.account.AccountVoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST2_ID;
import static ru.graduation.voting.web.controller.open.OpenTestData.rest1;

@SpringBootTest(classes = ClockDisableVote.class)
public class AfterVoteCloseControllerTest extends AbstractVoteControllerTest {

    private static final String URL = VOTE_URL + "/";

    @Test
    @WithUserDetails(USER_MAIL1)
    void createVote() throws Exception {
        Vote firstVote = getFirstVote();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(rest1)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(firstVote))
                .andExpect(status().isCreated());

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        MATCHER.assertMatch(created, firstVote);
        firstVote.setUser(user1);
        MATCHER.assertMatch(repository.getById(newId), firstVote);
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void updateVote() throws Exception {
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(rest1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_VOTE_CLOSE)));
    }
}