package ru.graduation.voting.web.controller.open;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.AbstractControllerTest;
import ru.graduation.voting.model.Restaurant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.controller.open.TestData.REST1_ID;
import static ru.graduation.voting.web.controller.open.TestData.RESTAURANT_MATCHER;


class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.OPEN_REST_URL + "/";

    @Test
    void getById() throws Exception {
        Restaurant excepted = TestData.rest1;
        excepted.setMenu(null);
        perform(MockMvcRequestBuilders.get(REST_URL + REST1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(excepted));
    }

    @Test
    void getByIdWithMenu() {
    }

    @Test
    void getByIdWithMenuBetweenDate() {
    }

    @Test
    void get() {
    }

    @Test
    void getWithTodayMenu() {
    }

    @Test
    void getWithMenuBetweenDate() {
    }
}