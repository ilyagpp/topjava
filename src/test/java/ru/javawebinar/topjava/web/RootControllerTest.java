package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.MealTestData;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(3)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(UserTestData.user.getName()))
                        )
                )));
    }

    @Test
    void  getMeals() throws Exception {
        perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(7)));
    }

    public static class ResourceControllerTest extends  AbstractControllerTest{

        @Test
        void getResources() throws Exception {
            perform(get("/resources/css/style.css"))
                    .andDo(print())
                    .andExpect(content().contentType("text/css;charset=UTF-8"))
                    .andExpect(status().isOk());

        }


    }
}