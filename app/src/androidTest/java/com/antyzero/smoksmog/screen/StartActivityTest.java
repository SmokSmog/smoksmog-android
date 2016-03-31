package com.antyzero.smoksmog.screen;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.rules.RxSchedulerTestRule;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith( AndroidJUnit4.class )
@LargeTest
public class StartActivityTest {

    @Rule
    public final ActivityTestRule<StartActivity> activityTestRule = new ActivityTestRule<>( StartActivity.class );
    @Rule
    public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();

    @Test
    public void checkCreation() {

        Activity activity = activityTestRule.getActivity();

        Espresso.registerIdlingResources( rxSchedulerTestRule.getThreadPoolIdlingResource() );

        onView( withId( R.id.viewPager ) ).perform( click() );
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        Spoon.screenshot( activity, "Creation" );

    }
}
