package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repo.MealReository;
import ru.javawebinar.topjava.Repo.MealRepo;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    MealRepo mealRepo = new MealRepo().init();



    private final static Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("IN MEAL SERVLET");

        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealRepo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALLORIESPERDAY);
        req.setAttribute("meals", mealTos);
        req.getRequestDispatcher("/meal.jsp").forward(req, resp);
    }
}
