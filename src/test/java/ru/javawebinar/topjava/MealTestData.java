package ru.javawebinar.topjava;

import javafx.beans.binding.BooleanExpression;
import org.springframework.dao.support.DataAccessUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static int MEAL_ID = START_SEQ + 3;

    public static int MEAL_IDGET = 100004;

    public static final int NOT_FOUND = 10;

    public static final LocalDate start = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate end = LocalDate.of(2020, Month.JANUARY,30);

    public static final Map<Integer, List<Meal>> testMap = new HashMap<>();

    static {
        List<Meal> userMeal = Arrays.asList(
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510)));
        userMeal.forEach(meal -> meal.setId(getNextId()));
        testMap.put(USER_ID, userMeal);
        List<Meal> adminMeal = Arrays.asList(
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510)),
                (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500)));
        adminMeal.forEach(meal -> meal.setId(getNextId()));
        testMap.put(ADMIN_ID, adminMeal);
    }

    private static int getNextId() {
        return MEAL_ID++;
    }

    public static void save(Meal meal, int userId) {
        meal.setId(getNextId());

    }

    public static Meal get (int mealId, int userid){
        List<Meal> result = new ArrayList<>();
        testMap.get(userid).forEach(meal -> {
            if (meal.getId().equals(mealId)) {
                result.add(meal);
            };
        });
        return DataAccessUtils.singleResult(result);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, Month.MARCH, 3, 10, 0), "Новая еда", 550);
    }

    public static List<Meal> getAll(int userId) {

        List<Meal> getAll = testMap.get(userId);
        sort(getAll);
        return getAll;
    }

    public static List<Meal> getBetweenInclusive(){

        List<Meal> result = testMap.get(USER_ID).stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime().toLocalDate(), start, end))
                .collect(Collectors.toList());
        sort(result);
        return result;
    }

    private static void sort(List<Meal> getAll){
        getAll.sort((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()));
    }


    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
