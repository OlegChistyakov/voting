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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.util.JsonUtil.writeValue;
import static ru.graduation.voting.web.controller.admin.AdminTestData.*;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.ADMIN_DISH_URL
            .replaceAll("\\{id}", "100003") + '/';

    @Autowired
    DishRepository repository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNewDish();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andExpect(status().isCreated());
        //newDish.setRestaurant(exist_rest);

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

//    @Test
//    @WithUserDetails(ADMIN_MAIL)
//    void createDuplicate() throws Exception {
//        Dish duplicate = AdminTestData.exist_rest;
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(duplicate)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string(containsString(EXCEPTION_Dish_DUPLICATE_NAME)));
//    }
//
//    @Test
//    @WithUserDetails(ADMIN_MAIL)
//    void update() throws Exception {
//        Dish updated = getUpdateDish();
//        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_REST_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        Dish_MATCHER.assertMatch(repository.getById(EXIST_REST_ID), getUpdateDish());
//    }
//
//    @Test
//    @WithUserDetails(ADMIN_MAIL)
//    void updateNotExist() throws Exception {
//        Dish updated = getUpdateDish();
//        updated.setId(null);
//        perform(MockMvcRequestBuilders.put(REST_URL + NON_EXIST_ENTITY_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string(containsString(EXCEPTION_NOT_EXIST_ENTITY)));
//    }
//
//    @Test
//    @WithUserDetails(ADMIN_MAIL)
//    void updateDuplicate() throws Exception {
//        Dish duplicate = AdminTestData.exist_rest;
//        duplicate.setId(null);
//        perform(MockMvcRequestBuilders.put(REST_URL + (EXIST_REST_ID + 1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(duplicate)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string(containsString(EXCEPTION_Dish_DUPLICATE_NAME)));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + EXIST_REST_ID))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        assertFalse(repository.findById(EXIST_REST_ID).isPresent());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void deleteNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + NON_EXIST_ENTITY_ID))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    void deleteUnAuth() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + EXIST_REST_ID))
//                .andExpect(status().isUnauthorized());
//    }
}