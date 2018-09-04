package com.parassidhu.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.parassidhu.bakingapp.ui.main.MainActivity;
import com.parassidhu.bakingapp.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepListActivityTest {

    private static final String SIFTED_CAKE_FLOUR = "sifted cake flour";
    private static final String RECIPE_INTRODUCTION = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void checkStepListDisplayed() {
        onView(withId(R.id.recipe_list))
                .perform(actionOnItemAtPosition(2, click()));

        onView(withText(RECIPE_INTRODUCTION))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIngredientsDisplayed() {
        onView(withId(R.id.recipe_list))
                .perform(actionOnItemAtPosition(2, click()));

        onView(withText(SIFTED_CAKE_FLOUR))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
