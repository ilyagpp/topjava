package ru.javawebinar.topjava.MealService;

import ru.javawebinar.topjava.Repo.MealRepository;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealService {

    MealRepository mealRepository;


    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAll() {
        return mealRepository.getAll();
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal update (Long id, Meal meal){
        return mealRepository.update(id, meal);
    };

    public boolean delete(Long id) {
        return mealRepository.delete(id);
    }

    public Meal findById(Long id) {
        return mealRepository.findById(id);
    }


}
