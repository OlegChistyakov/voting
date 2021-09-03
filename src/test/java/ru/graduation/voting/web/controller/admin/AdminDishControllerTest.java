package ru.graduation.voting.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.AbstractControllerTest;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.repository.DishRepository;
import ru.graduation.voting.web.controller.open.OpenTestData;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.util.JsonUtil.writeValue;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_DISH_NAME;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.controller.UserTestData.ADMIN_MAIL;
import static ru.graduation.voting.web.controller.admin.AdminTestData.*;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String DISH_URL = AdminDishController.ADMIN_DISH_URL
            .replaceAll("\\{id}", String.valueOf(EXIST_REST_ID)) + '/';

    @Autowired
    DishRepository repository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNewDish();
        ResultActions action = perform(MockMvcRequestBuilders.post(DISH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "", 1, LocalDate.now(), OpenTestData.rest1);
        perform(MockMvcRequestBuilders.post(DISH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicateName() throws Exception {
        Dish dish = getNewDish();
        dish.setDescription("today's dish_1 from rest_1");
        perform(MockMvcRequestBuilders.post(DISH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(dish)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_DISH_NAME)));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdateDish();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(DISH_URL + EXIST_DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getById(EXIST_DISH_ID), getUpdateDish());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicateName() throws Exception {
        Dish updated = getUpdateDish();
        updated.setDescription("today's dish_2 from rest_1");
        perform(MockMvcRequestBuilders.post(DISH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_DISH_NAME)));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateNotExist() throws Exception {
        Dish updated = getUpdateDish();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(DISH_URL + NON_EXIST_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(DISH_URL + EXIST_DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(EXIST_DISH_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(DISH_URL + NON_EXIST_ENTITY_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(DISH_URL + EXIST_DISH_ID))
                .andExpect(status().isUnauthorized());
    }
}