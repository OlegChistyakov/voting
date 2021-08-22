package ru.graduation.voting.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.AbstractControllerTest;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.util.JsonUtil.writeValue;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_RESTAURANT_DUPLICATE_NAME;
import static ru.graduation.voting.web.controller.admin.AdminTestData.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.ADMIN_REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getById(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant duplicate = AdminTestData.exist_rest;
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_RESTAURANT_DUPLICATE_NAME)));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = getUpdateRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_REST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getById(EXIST_REST_ID), getUpdateRestaurant());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateNotExist() throws Exception {
        Restaurant updated = getUpdateRestaurant();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + NON_EXIST_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Restaurant duplicate = AdminTestData.exist_rest;
        duplicate.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + (EXIST_REST_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_RESTAURANT_DUPLICATE_NAME)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + EXIST_REST_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(EXIST_REST_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NON_EXIST_ENTITY_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + EXIST_REST_ID))
                .andExpect(status().isUnauthorized());
    }
}