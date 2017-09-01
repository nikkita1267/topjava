package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService service;

    private final BeanMatcher<MealWithExceed> matcher = BeanMatcher.of(MealWithExceed.class);

    private static final String URL = MealRestController.PATH + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + (MEAL1_ID + 1)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Arrays.asList(
                MEAL6, MEAL5, MEAL4, MEAL3, MEAL1
        ), service.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(matcher.contentListMatcher(MealsUtil.getWithExceeded(service.getAll(USER_ID), AuthorizedUser.getCaloriesPerDay())));
    }

    @Test
    public void testCreateWithLocate() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Test Meal", 1200);

        ResultActions actions = mockMvc.perform(post(URL)
            .contentType(APPLICATION_JSON_VALUE)
            .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal get = MATCHER.fromJsonAction(actions);

        newMeal.setId(get.getId());
        MATCHER.assertEquals(newMeal, get);
        MATCHER.assertListEquals(
                Arrays.asList(
                        newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1
                ), service.getAll(USER_ID)
        );

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Updated hah");

        mockMvc.perform(put(URL + MEAL1_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(URL + "filter/2015-05-30T10:00:00/2015-05-30T20:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(matcher.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL3, MEAL2, MEAL1), AuthorizedUser.getCaloriesPerDay())));
    }
}