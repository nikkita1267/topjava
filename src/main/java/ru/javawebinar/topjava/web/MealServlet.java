package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealServlet extends HttpServlet {
    private List<Meal> meals = new CopyOnWriteArrayList<>();
    {
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 13, 7, 1),
                "Завтрак",
                500
        ));
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 13, 12, 1),
                "Обед",
                540
        ));
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 13, 18, 1),
                "Ужин",
                750
        ));
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 12, 7, 1),
                "Завтрак",
                228
        ));
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Обед",
                1000
        ));
        meals.add(new Meal(
                LocalDateTime.of(2010, 12, 12, 18, 1),
                "Ужин",
                900
        ));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(
                meals, LocalTime.MIN, LocalTime.MAX, 2000
        );

        request.setAttribute("meals", mealsWithExceed);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
