package com.ogettoweb.dealers.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.fragments.CartFragment;
import com.ogettoweb.dealers.fragments.HomeFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() throws Exception {

    }

    @Test
    public void testUi() throws Exception {
        onView(withId(R.id.home)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.cart)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.menu)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void home() throws Exception {
        onView(withId(R.id.home)).perform(click()).check(matches(isDisplayed()));
        activityRule.getActivity().getSupportFragmentManager().findFragmentByTag(HomeFragment.class.toString());
    }

    @Test
    public void cart() throws Exception {
        onView(withId(R.id.cart)).perform(click()).check(matches(isDisplayed()));
        activityRule.getActivity().getSupportFragmentManager().findFragmentByTag(CartFragment.class.toString());
    }

    @Test
    public void menu() throws Exception {
        onView(withId(R.id.menu)).perform(click()).check(matches(isDisplayed()));
        onView(withText("Search")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
