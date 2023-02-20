package ru.javawebinar.topjava.Repo;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealStorage implements MealRepository {

    private static MealStorage instance;
    private  List<Meal> storage;
    private Long id = 0L;

    public MealStorage() {
        init();
    }

    private Long getNewId() {
        this.id++;
        return id;
    }

    public void init() {
        List<Meal> meals = Arrays.asList(
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        storage = new CopyOnWriteArrayList<>(meals);
    }


    public static MealStorage getStorage() {
        if (instance == null){
            return new MealStorage();
        } else return instance;
    }

    @Override
    public List<Meal> getAll() {
        return storage;
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew(meal.getId())) {
            meal.setId(getNewId());
            storage.add(meal);
        } else {

            meal = update(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public Meal update(Long id, Meal meal) {
        int index = getIndexById(id);
        if (index != -1) {

            storage.remove(index);
            storage.add(index, meal);
        }
        return meal;
    }

    @Override
    public boolean delete(Long id) {
        int index = getIndexById(id);

        if (index != -1) {

            storage.remove(index);
            return true;
        } else

            return false;
    }

    @Override
    public Meal findById(Long id) {
        return storage.get(getIndexById(id));
    }

    private Integer getIndexById(Long id) {

        for (int i = 0; i < storage.size(); i++) {

            if (Objects.equals(storage.get(i).getId(), id)) {
                return i;
            }
        }
        return -1;
    }

}
