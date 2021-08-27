package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.controller.account.AccountData.*;

class VoteControllerTest extends AbstractVoteControllerTest {

    @Test
    @WithUserDetails(USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentJson(votes));
    }

    @Test
    void get() {
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void toVote() throws Exception {

    }
}