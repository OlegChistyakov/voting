package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.web.controller.config.ClockEnableVote;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.graduation.voting.web.controller.account.AccountData.*;
import static ru.graduation.voting.web.controller.account.VoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST1_ID;

@SpringBootTest(classes = ClockEnableVote.class)
public class BeforeVoteCloseControllerTest extends AbstractVoteControllerTest {

    private static final String URL = VOTE_URL + "/";

    @Test
    @WithUserDetails(USER_MAIL)
    void firstVote() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(URL + REST1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(firstVote));

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        MATCHER.assertMatch(created, firstVote);
        firstVote.setUser(user);
        MATCHER.assertMatch(repository.getById(newId), firstVote);
    }
}
