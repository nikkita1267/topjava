package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealTestData {

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                    && Objects.equals(expected.getCalories(), actual.getCalories())
                    && Objects.equals(expected.getDateTime(), actual.getDateTime())
                    && Objects.equals(expected.getDescription(), actual.getDescription()))
    );

    public static final int BREAKFAST_ID = BaseEntity.START_SEQ;
    public static final int DINNER_ID = BaseEntity.START_SEQ + 1;

    public static final Meal TEST_MEAL_BREAKFAST_FOR_USER = new Meal(BREAKFAST_ID, LocalDateTime.of(2017, 12, 12, 7, 10),
            "Breakfast", 150);
    public static final Meal TEST_MEAL_DINNER_FOR_ADMIN = new Meal(DINNER_ID, LocalDateTime.of(2017, 12, 13, 14, 20),
            "Dinner", 1000);
}
