package com.parassidhu.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.ui.detail.StepListActivity;
import com.parassidhu.bakingapp.ui.main.MainActivity;
import com.parassidhu.bakingapp.utils.Constants;
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
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String YELLOW_CAKE = "Yellow Cake";

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void clickRecyclerViewItem_CheckThirdElement(){
        onView(withId(R.id.recipe_list)).perform(actionOnItemAtPosition(2, click()));

        onView(withText(YELLOW_CAKE))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerViewItem_CheckIntent(){
        onView(withId(R.id.recipe_list))
                .perform(actionOnItemAtPosition(2, click()));

        intended(
                allOf(
                        hasComponent(StepListActivity.class.getName()),
                        hasExtra(equalTo(Constants.RECIPE_NAME), equalTo(YELLOW_CAKE))
                )
        );
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
