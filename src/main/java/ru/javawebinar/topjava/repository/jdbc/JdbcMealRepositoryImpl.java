package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private final DataSourceTransactionManager manager;

    private static final RowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(
            DataSource dataSource,
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            DataSourceTransactionManager manager
    ) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.manager = manager;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus st = manager.getTransaction(def);
        try {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories())
                    .addValue("date_time", meal.getDateTime())
                    .addValue("user_id", userId);

            if (meal.isNew()) {
                Number newId = insertMeal.executeAndReturnKey(map);
                meal.setId(newId.intValue());
            } else {
                if (namedParameterJdbcTemplate.update("" +
                                "UPDATE meals " +
                                "   SET description=:description, calories=:calories, date_time=:date_time " +
                                " WHERE id=:id AND user_id=:user_id"
                        , map) == 0) {
                    return null;
                }
            }
            manager.commit(st);
            return meal;
        } catch (Exception e){
            manager.rollback(st);
            throw e;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}
