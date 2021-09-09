package ru.graduation.voting.web.controller.account;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.controller.UserTestData.USER_MAIL2;
import static ru.graduation.voting.web.controller.account.AccountData.*;
import static ru.graduation.voting.web.controller.UserTestData.USER_MAIL1;

class AccountAdminVoteControllerTest extends AbstractVoteControllerTest {

    @Test
    @WithUserDetails(USER_MAIL1)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentJson(votesUser1));
    }

    @Test
    @WithUserDetails(USER_MAIL1)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + vote2.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentJson(vote2));
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentJson(vote3));
    }

    @Test
    @WithUserDetails(USER_MAIL1)
    void getNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + vote3.id()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }
}