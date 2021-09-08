package ru.graduation.voting.web.controller.open;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.controller.open.OpenTestData.*;


class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.OPEN_REST_URL + "/";

    @BeforeEach
    void clearMenu() {
        allRestaurants.forEach(r -> r.setMenu(null));
    }

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    void getByIdWithMenuBetweenDate() throws Exception {
        rest1.setMenu(getMenu(REST1_ID));
        perform(MockMvcRequestBuilders
                .get(REST_URL + REST1_ID + "/with-menu/by?startDate=" + yesterday + "&endDate=" + today))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    void getByIdWithMenuFromNullDate() throws Exception {
        rest1.setMenu(getMenuByDate(REST1_ID, yesterday));
        perform(MockMvcRequestBuilders
                .get(REST_URL + REST1_ID + "/with-menu/by?startDate=&endDate=" + yesterday))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    void getByIdWithMenuToNullDate() throws Exception {
        rest1.setMenu(getMenuByDate(REST1_ID, today));
        perform(MockMvcRequestBuilders
                .get(REST_URL + REST1_ID + "/with-menu/by?startDate=" + today + "&endDate="))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(allRestaurants));
    }

    @Test
    void getAllWithTodayMenu() throws Exception {
        rest1.setMenu(getMenuByDate(REST1_ID, today));
        rest2.setMenu(getMenuByDate(REST2_ID, today));
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu/today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(rest1, rest2)));
    }

    @Test
    void getWithMenuBetweenDate() throws Exception {
        rest1.setMenu(getMenuByDate(REST1_ID, today));
        rest2.setMenu(getMenuByDate(REST2_ID, today));
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu/by?startDate=" + today + "&endDate=" + today))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(rest1, rest2)));
    }
}