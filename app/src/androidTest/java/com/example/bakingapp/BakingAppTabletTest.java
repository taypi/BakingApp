package com.example.bakingapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.view.activities.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Usually there is test for each screen, but since this app is very simples
 * is not worth it creating different tests for each one, just one class
 * is enough to hold all basic test cases.
 * <p>
 * All tests must be run with the device in portrait
 */
@RunWith(AndroidJUnit4.class)
public class BakingAppTabletTest {

    private static final int FIRST_RECIPE = 0;
    private static final int FIRST_STEP = 0;

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void itDisplaysStepsDetailsInTablet() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));

        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(FIRST_STEP, click()));
        onView(allOf(withId(R.id.tv_description), isDisplayed())).check(matches(withText("Recipe Introduction")));
        onView(allOf(withId(R.id.exo_overlay), isDisplayed())).check(matches(isDisplayed()));
    }

}
