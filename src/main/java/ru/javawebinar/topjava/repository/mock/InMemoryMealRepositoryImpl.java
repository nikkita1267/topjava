package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, AuthorizedUser.id()));
    }

    @Override
    public Meal save(Meal meal, int reqId) {
        if (reqId != meal.getOwnerId())
            return null;
        log.info("Save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int reqId) {
        if (reqId != repository.get(id).getOwnerId())
            return false;
        log.info("Delete {}", id);
        return (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int reqId) {
        Meal temp = repository.get(id);
        if (temp.getOwnerId() != reqId)
            return null;
        log.info("Get {}", id);
        return temp;
    }

    @Override
    public List<Meal> getAll(int reqId) {
        if (repository.size() == 0)
            return Collections.emptyList();
        log.info("Get all");
        if (repository.get(1).getOwnerId() != reqId)
            return null;
        return repository.values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

