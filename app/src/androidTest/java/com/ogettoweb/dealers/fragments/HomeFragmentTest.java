package com.ogettoweb.dealers.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.activities.MainActivity;

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
public class HomeFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() throws Exception {

    }

    @Test
    public void testUi() throws Exception {
        onView(withText(R.string.dealers_list)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void search() throws Exception {
        onView(withId(R.id.search)).perform(click()).check(matches(isDisplayed()));
        onView(withText("Menu click")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
