package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {
    @Autowired
    MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.MEAL_REST_URL + "/" + MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.MEAL_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meals));
    }

    @Test
    void createMealWithLocation() {

    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(MealRestController.MEAL_REST_URL + "/" + MEAL1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Meal meal = mealService.get(MEAL1_ID, User.START_SEQ);

        MEAL_MATCHER.assertMatch(meal, updated);
    }

    @Test
    void getBetween() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("startDateTime", Collections.singletonList("2011-12-03T10:15:30"));
        params.put("endDateTime", Collections.singletonList("2011-12-03T11:15:30"));
        perform(MockMvcRequestBuilders.get(MealRestController.MEAL_REST_URL).params(params))
                .andDo(print())
                .andExpect(status().isOk());
    }


}