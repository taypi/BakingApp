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

@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    private static final int FIRST_RECIPE = 0;
    private static final int FOURTH_STEP = 3;

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void itShowsRecipeIngredients() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));

        onView(withText("Graham Cracker crumbs")).check(matches(isDisplayed()));
        onView(withText("unsalted butter, melted")).check(matches(isDisplayed()));
        onView(withText("granulated sugar")).check(matches(isDisplayed()));
        onView(withText("salt")).check(matches(isDisplayed()));
        onView(withText("vanilla")).check(matches(isDisplayed()));
        onView(withText("Nutella or other chocolate-hazelnut spread")).check(matches(isDisplayed()));
        onView(withText("Mascapone Cheese(room temperature)")).check(matches(isDisplayed()));
        onView(withText("heavy cream(cold)")).check(matches(isDisplayed()));
        onView(withText("cream cheese(softened)")).check(matches(isDisplayed()));
    }

    @Test
    public void itShowsRecipeSteps() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));
        onView(withId(R.id.view_pager_tabs)).perform(swipeLeft());

        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
        onView(withText("Starting prep")).check(matches(isDisplayed()));
        onView(withText("Prep the cookie crust.")).check(matches(isDisplayed()));
        onView(withText("Press the crust into baking form.")).check(matches(isDisplayed()));
        onView(withText("Start filling prep")).check(matches(isDisplayed()));
        onView(withText("Finish filling prep")).check(matches(isDisplayed()));
        onView(withText("Finishing Steps")).check(matches(isDisplayed()));
    }

    @Test
    public void itDisplaysTheStepDetails() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));
        onView(withId(R.id.view_pager_tabs)).perform(swipeLeft());
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));

        onView(allOf(withId(R.id.tv_description), isDisplayed())).check(matches(withText("Recipe Introduction")));
        onView(allOf(withId(R.id.exo_overlay), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void itAllowsNavigationBetweenSteps() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(FIRST_RECIPE, click()));
        onView(withId(R.id.view_pager_tabs)).perform(swipeLeft());
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(FOURTH_STEP, click()));

        onView(withId(R.id.view_pager_steps)).perform(swipeRight());
        onView(allOf(withId(R.id.tv_description), isDisplayed())).check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

        onView(withId(R.id.view_pager_steps)).perform(swipeRight());
        onView(allOf(withId(R.id.tv_description), isDisplayed())).check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));
    }
}
