package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void testGetUserWithMealID() throws Exception {
        Assert.assertEquals(
                service.get(MEAL1_ID, User.START_SEQ).getUser().getId(),
                Integer.valueOf(User.START_SEQ)
        );
    }

}
