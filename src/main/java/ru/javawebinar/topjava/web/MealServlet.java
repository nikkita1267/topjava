package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.FakeMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
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

public class MealServlet extends HttpServlet {
    private int caloriesPerDay = 2000;
    private MealDao dao = new FakeMealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);

        req.setAttribute("meals", mealsWithExceed);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("method");
        if (action == null)
            action = "";
        switch (action)
        {
            case "add":
                String descAdd = req.getParameter("description");
                LocalDateTime timeAdd = LocalDateTime.parse(req.getParameter("date_time"));
                int caloriesAdd = Integer.parseInt(req.getParameter("calories"));
                dao.add(new Meal(timeAdd, descAdd, caloriesAdd));
                break;
            case "update":
                int idUpd = Integer.parseInt(req.getParameter("id"));
                String descUpd = req.getParameter("description");
                LocalDateTime timeUpd = LocalDateTime.parse(req.getParameter("date_time"));
                int caloriesUpd = Integer.parseInt(req.getParameter("calories"));
                Meal meal = new Meal(timeUpd, descUpd, caloriesUpd);
                meal.setId(idUpd);
                dao.update(idUpd, meal);
                break;
            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                break;
        }
        doGet(req, resp);
    }
}
