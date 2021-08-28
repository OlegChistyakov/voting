package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.web.controller.config.ClockDisableVote;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_METHOD_NOT_ALLOWED;
import static ru.graduation.voting.web.controller.account.VoteController.VOTE_URL;
import static ru.graduation.voting.web.controller.open.OpenTestData.REST1_ID;
import static ru.graduation.voting.web.controller.user.UserTestData.USER_MAIL1;

@SpringBootTest(classes = ClockDisableVote.class)
public class AfterVoteCloseControllerTest extends AbstractVoteControllerTest {

    private static final String URL = VOTE_URL + "/";

    @Test
    @WithUserDetails(USER_MAIL1)
    void toVote() throws Exception {
        perform(MockMvcRequestBuilders.post(URL + REST1_ID))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(containsString(EXCEPTION_METHOD_NOT_ALLOWED)));
    }
}
