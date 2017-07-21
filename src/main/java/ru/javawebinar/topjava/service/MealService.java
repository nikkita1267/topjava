package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal save(Meal meal, int requesterId) throws NotFoundException;

    void delete(int id, int requesterId) throws NotFoundException;

    void update(Meal meal, int requesterId) throws NotFoundException;

    Meal get(int id, int requesterId) throws NotFoundException;

    List<Meal> getAll(int requesterId) throws NotFoundException;
}