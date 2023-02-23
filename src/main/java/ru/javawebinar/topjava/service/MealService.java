package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Service
public class MealService {


    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public List<Meal> getAll() {
        return (List<Meal>) repository.getAll();
    }

    public Meal findById(int id) {
        Meal meal = repository.get(id, SecurityUtil.authUserId());
        ValidationUtil.checkNotFound(SecurityUtil.authUserId()==meal.getUserId(), "meal does not belong to this user");
        return meal;

    }

    public boolean deleteById(int id) {
        Meal  meal = repository.get(id, SecurityUtil.authUserId());

        ValidationUtil.checkNotFound(SecurityUtil.authUserId()==meal.getUserId(), "meal does not belong to this user");

        return repository.delete(id, SecurityUtil.authUserId());
    }

    public Meal save(Meal meal) {

        if (!meal.isNew()){
            ValidationUtil.checkNotFound(SecurityUtil.authUserId()==meal.getUserId(), "meal does not belong to this user");
        }

        return repository.save(meal, SecurityUtil.authUserId());

    }
}