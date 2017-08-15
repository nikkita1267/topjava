package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator populator;

    @Before
    public void setUp() throws Exception {
        populator.execute();
    }

    @Test
    public void testSave() throws Exception {
        int userId = AuthorizedUser.id();
        Meal meal = new Meal(LocalDateTime.now(), "Test", 100);
        Meal saved = service.save(meal, userId);

        meal.setId(saved.getId());
        switch (AuthorizedUser.id()) {
            case USER_ID:
                MATCHER.assertCollectionEquals(Arrays.asList(TEST_MEAL_BREAKFAST_FOR_USER, meal), service.getAll(userId));
                break;
            case ADMIN_ID:
                MATCHER.assertCollectionEquals(Arrays.asList(TEST_MEAL_DINNER_FOR_ADMIN, meal), service.getAll(userId));
                break;
        }
    }

    @Test
    public void testGet() throws Exception {
        Meal expect = service.get(BREAKFAST_ID, AuthorizedUser.id());
        MATCHER.assertEquals(TEST_MEAL_BREAKFAST_FOR_USER, expect);
    }

    @Test(expected = NotFoundException.class)
    public void testAnotherUserAccess() throws Exception {
        int user_id = getAnotherUser();
        service.get(BREAKFAST_ID, user_id);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        service.delete(2, AuthorizedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteFromAntherUser() throws Exception {
        service.delete(0, getAnotherUser());
    }

    @Test
    public void testGetBetween() throws Exception {
        switch (AuthorizedUser.id()) {
            case USER_ID:
                MATCHER.assertEquals(TEST_MEAL_BREAKFAST_FOR_USER,
                        service.getBetweenDateTimes(LocalDateTime.of(2017, 12, 11, 6, 20),
                                LocalDateTime.of(2017, 12, 14, 14, 20),
                                AuthorizedUser.id()).get(0));
                break;
            case ADMIN_ID:
                MATCHER.assertEquals(TEST_MEAL_DINNER_FOR_ADMIN,
                        service.getBetweenDateTimes(LocalDateTime.of(2017, 12, 13, 14, 20),
                                LocalDateTime.of(2017, 12, 14, 14, 20),
                                AuthorizedUser.id()).get(0));
                break;
        }

    }

    private int getAnotherUser() {
        switch (AuthorizedUser.id()) {
            case UserTestData.USER_ID:
                return UserTestData.ADMIN_ID;
            case UserTestData.ADMIN_ID:
               return UserTestData.USER_ID;
            default:
                return  -1;
        }
    }
}
