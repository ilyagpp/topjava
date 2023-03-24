package ru.javawebinar.topjava.web;

import org.hibernate.type.LocalDateTimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);


    private final MealService mealService;

    @Autowired
    public JspMealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate,
                         @RequestParam(required = false) String startTime,
                         @RequestParam(required = false) String endTime,
                         Model model) {
        List<MealTo> mealToList = new ArrayList<>();
        if (startDate != null || endDate != null || startTime != null || endTime != null){

            mealToList = MealsUtil.getFilteredTos(mealService.getBetweenInclusive
                    (DateTimeUtil.parseLocalDate(startDate), DateTimeUtil.parseLocalDate(endDate) ,SecurityUtil.authUserId()),
                    SecurityUtil.authUserCaloriesPerDay(),
                    DateTimeUtil.parseLocalTime(startTime), DateTimeUtil.parseLocalTime(endTime));
        } else {
            mealToList = MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
            log.info("getAll");
        }

        model.addAttribute("meals", mealToList);
        return "meals";
    }

    @GetMapping("/edit={id}")
    public String getById(@PathVariable Integer id,
                          Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        model.addAttribute("meal", mealService.get(id, userId));
        return "mealForm";
    }

    @GetMapping("/new")
    public String getById(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get for new meal for user {}", userId);

        LocalDateTime ldt = DateTimeUtil.parseLocalDateTime(LocalDateTime.now().format(DateTimeUtil.DATE_TIME_FORMATTER));

        Meal meal = new Meal(ldt, "", 100);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete={id}")
    public String deleteById(@PathVariable Integer id,
                             Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @PostMapping("")
    public String save(@RequestParam(required = false) Integer id,
                       @RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam Integer calories) {
        int userId = SecurityUtil.authUserId();

        Meal meal = new Meal();
        meal.setId(id);
        meal.setDateTime(DateTimeUtil.parseLocalDateTime(dateTime));
        meal.setDescription(description);
        meal.setCalories(calories);

        if (meal.isNew()) {
            log.info("create new meal for user {}", userId);
            User user = new User();
            user.setId(userId);
            meal.setUser(user);
            mealService.create(meal, userId);
        } else {
            log.info("update meal {} for user {}", meal.id(), userId);
            mealService.update(meal, userId);
        }
        return "redirect:/meals";
    }


}
