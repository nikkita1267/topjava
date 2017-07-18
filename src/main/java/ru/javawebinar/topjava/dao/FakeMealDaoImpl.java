package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FakeMealDaoImpl implements MealDao {

    private List<Meal> meals = new CopyOnWriteArrayList<>(
            Arrays.asList(
                    new Meal(LocalDateTime.of(2000, 12, 13, 12, 0), "Обед", 200),
                    new Meal(LocalDateTime.of(2000, 12, 13, 7, 0), "Завтрак", 700),
                    new Meal(LocalDateTime.of(2000, 12, 13, 21, 0), "Ужин", 400),
                    new Meal(LocalDateTime.of(2000, 12, 15, 12, 0), "Обед", 400),
                    new Meal(LocalDateTime.of(2000, 12, 15, 7, 0), "Завтрак", 1000),
                    new Meal(LocalDateTime.of(2000, 12, 15, 21, 0), "Ужин", 700)
            )
    );

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id - 1);
    }

    @Override
    public void update(int id, Meal meal) {
        meals.set(id - 1, meal);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id - 1);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }
}
