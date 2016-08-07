package com.ogettoweb.dealers.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.activities.MainActivity;
import com.ogettoweb.dealers.models.Dealer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DealersFragmentTest {

    private DealersFragment dealersFragment;
    private List<Dealer> dealers;
    private AppCompatActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {
        activity = activityTestRule.getActivity();
        onView(withId(R.id.dealers)).perform(click());
        dealers = dealersFragment.getDealers();
    }

    @Test
    public void testUI() throws Exception {
        onView(withId(R.id.alphabet)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.distance)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void titleText() throws Exception {
        onView(withId(R.id.back_text)).check(matches(isDisplayed()));
        assertEquals(activity.getString(R.string.on_map), ((TextView) activity.findViewById(R.id.back_text)).getText().toString());
    }

    @Test
    public void items() throws Exception {
        for (int position = 0; position < dealers.size(); position++) {
            onView(withText(dealers.get(position).getCompany().toString()))
                    .check(matches(isDisplayed()));
        }

    }
}
