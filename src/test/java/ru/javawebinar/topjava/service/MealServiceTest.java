package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() {
        Meal actual = mealService.get(MEAL_IDGET, USER_ID);
        Meal expected = MealTestData.get(MEAL_IDGET, USER_ID);
        MealTestData.assertMatch(actual, expected);
    }

    @Test()
    public void deleteNotFound(){
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void testDelete() {
        mealService.delete(MEAL_IDGET, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_IDGET ,USER_ID));

    }

    @Test
    public void testGetBetweenInclusive() {
        List<Meal> actual = mealService.getBetweenInclusive(start, end, USER_ID);
        List<Meal> exp = MealTestData.getAll(USER_ID).subList(4,7);
        MealTestData.assertMatch(actual, exp);
    }

    @Test
    public void testGetAll() {
        List<Meal> actual = mealService.getAll(USER_ID);
        List<Meal> expected = MealTestData.getAll(USER_ID);
        MealTestData.assertMatch(actual, expected);
    }

    public void testUpdate() {
    }

    public void testCreate() {
    }
}