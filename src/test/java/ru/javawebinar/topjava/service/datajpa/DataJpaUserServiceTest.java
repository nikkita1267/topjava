package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void testGetMealsByUserID() throws Exception {
        MATCHER.assertCollectionEquals(
                service.get(User.START_SEQ).getMeals(),
                Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6)
        );
    }
}
