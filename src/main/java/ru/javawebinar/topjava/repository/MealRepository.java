package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int reqId);

    boolean delete(int id, int reqId);

    Meal get(int id, int reqId);

    List<Meal> getAll(int reqId);
}
