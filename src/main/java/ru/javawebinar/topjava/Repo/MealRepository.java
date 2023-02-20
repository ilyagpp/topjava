package ru.javawebinar.topjava.Repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealRepository {

    List<Meal> getAll();

    Meal save(Meal meal);

    Meal update (Long id, Meal meal);

    boolean delete(Long id);

    Meal findById(Long id);


}
