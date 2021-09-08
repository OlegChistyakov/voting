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
import static ru.graduation.voting.util.JsonUtil.writeValue;
import static ru.graduation.voting.web.GlobalExceptionHandler.*;
import static ru.graduation.voting.web.controller.UserTestData.*;
import static ru.graduation.voting.web.controller.account.AccountData.MATCHER;
import static ru.graduation.voting.web.controller.account.AccountData.*;
import static ru.graduation.voting.web.controller.account.AccountVoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.rest1;
import static ru.graduation.voting.web.controller.open.OpenTestData.rest2;

@SpringBootTest(classes = ClockEnableVote.class)
public class BeforeVoteCloseControllerTest extends AbstractVoteControllerTest {

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
        Vote reVoting = getReVoting();
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(rest2)))
                .andDo(print())
                .andExpect(status().isNoContent());

        reVoting.setUser(user2);
        MATCHER.assertMatch(repository.getById(reVoting.id()), reVoting);
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void repeatCreateVoteToday() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(rest1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_REPEAT_POST_REQUEST)));
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void updateSameRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(rest1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_REPEAT_REQUEST)));
    }

    @Test
    @WithUserDetails(USER_MAIL1)
    void createVoteForNotExistRest() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1}"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }

    @Test
    @WithUserDetails(USER_MAIL1)
    void updateVoteForNotExistRest() throws Exception {
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1}"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }
}