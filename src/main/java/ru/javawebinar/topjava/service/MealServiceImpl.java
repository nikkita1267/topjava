package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int requesterId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, requesterId), meal.getId());
        return meal;
    }

    @Override
    public void delete(int id, int requesterId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, requesterId), id);
    }

    @Override
    public void update(Meal meal, int requesterId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, requesterId), meal.getId());
    }

    @Override
    public Meal get(int id, int requesterId) throws NotFoundException {
        Meal temp = repository.get(id, requesterId);
        checkNotFoundWithId(temp, id);
        return temp;
    }

    @Override
    public List<Meal> getAll(int requesterId) throws NotFoundException {
        List<Meal> meals = repository.getAll(requesterId);
        checkNotFound(meals, "type List");
        return meals;
    }
}