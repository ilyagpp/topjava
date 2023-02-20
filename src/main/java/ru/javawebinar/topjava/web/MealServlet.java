package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repo.MealRepository;
import ru.javawebinar.topjava.Repo.MealStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    MealRepository mealRepository = MealStorage.getStorage();


    private final static Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("IN MEAL SERVLET");

        Enumeration<String> names = request.getAttributeNames();

        if (request.getRequestURI().contains("meals/add") || request.getParameter("id") != null){

            if (request.getParameter("id")!= null) {
                Long id = Long.valueOf(request.getParameter("id"));
                request.setAttribute("meal", mealRepository.findById(id));
            }
            request.getRequestDispatcher("/mealForm.jsp").forward(request,response);
            return;
        }



        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALLORIESPERDAY);
        request.setAttribute("meals", mealTos);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        log.info("Meal method: POST");

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (parameterMap.containsKey("saveMeal")){

            LocalDateTime dateTime =  LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Long id = null;
            if (parameterMap.containsKey("id")) {
                id = Long.valueOf(request.getParameter("id"));
            }
            mealRepository.save(new Meal(id,dateTime,description,calories));
        }

        if (request.getParameter("deleteId")!= null) {
            Long id = Long.valueOf(request.getParameter("deleteId"));
            mealRepository.delete(id);
        }


        response.sendRedirect("/topjava/meals");
    }
}
