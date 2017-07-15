package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealServlet extends HttpServlet {
    private List<MealWithExceed> meals = new ArrayList<>();
    {
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Завтрак",
                500,
                true
        ));
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Обед",
                1000,
                true
        ));
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Ужин",
                750,
                true
        ));
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Завтрак",
                228,
                false
        ));
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Обед",
                1000,
                false
        ));
        meals.add(new MealWithExceed(
                LocalDateTime.of(2010, 12, 12, 12, 1),
                "Ужин",
                700,
                false
        ));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
