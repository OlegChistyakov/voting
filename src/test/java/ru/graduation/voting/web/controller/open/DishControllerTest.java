package ru.graduation.voting.web.controller.open;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.AbstractControllerTest;
import ru.graduation.voting.model.Dish;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.web.controller.open.OpenTestData.DISH_MATCHER;
import static ru.graduation.voting.web.controller.open.OpenTestData.dish1;

public class DishControllerTest extends AbstractControllerTest {

    private static final String DISH_URL = DishController.OPEN_DISH_URL + "/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(DISH_URL + dish1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1))
                .andDo(print());
    }

    @Test
    void getByName() throws Exception {
        List<Dish> dishes = List.of(dish1);
        perform(MockMvcRequestBuilders.get(DISH_URL + "/by?description=" + dish1.getDescription().toUpperCase()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishes))
                .andDo(print());
    }
}
