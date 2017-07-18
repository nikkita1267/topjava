package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void add(Meal meal);
    void delete(int id);
    void update(int id, Meal meal);
    Meal get(int id);
    List<Meal> getAll();
}
