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
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    private static final String RECIPE_INTRODUCTION = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void checkExoPlayerDisplayed(){
        onView(withId(R.id.recipe_list))
                .perform(actionOnItemAtPosition(2, click()));

        onView(withId(R.id.stepsList))
                .perform(actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.playerView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkDescriptionIsShown(){
        onView(withId(R.id.recipe_list))
                .perform(actionOnItemAtPosition(2, click()));

        onView(withId(R.id.stepsList))
                .perform(actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.description))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }


}
