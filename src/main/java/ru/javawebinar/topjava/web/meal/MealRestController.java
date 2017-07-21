package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public List<MealWithExceed> getFilteredByDate(LocalDate startDate, LocalDate endDate) {
        return getFilteredWithExceeded(service.getAll(AuthorizedUser.id()), startDate, endDate, AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredByTime(LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExceeded(service.getAll(AuthorizedUser.id()), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAll() {
        return getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal add(Meal meal) {
        ValidationUtil.checkNew(meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        service.update(meal, AuthorizedUser.id());
    }

    public void delete(Meal meal) {
        service.delete(meal.getId(), AuthorizedUser.id());
    }

    public MealWithExceed get(int id) {
        return getAll().get(id);
    }

    public List<MealWithExceed> get(String search) {
        return  getAll()
                .stream()
                .filter(mealWithExceed -> {
                    String text = DateTimeUtil.toString(mealWithExceed.getDateTime()) +
                            mealWithExceed.getDescription() + mealWithExceed.getCalories();

                    return text.toLowerCase().contains(search.toLowerCase());
                })
                .collect(Collectors.toList());
    }
}