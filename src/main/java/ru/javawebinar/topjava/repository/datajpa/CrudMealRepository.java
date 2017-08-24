package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> getAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime start, LocalDateTime end, int userId);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    Meal getByIdAndUserId(int id, int userId);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User getUser(@Param("userId") int id);
}
