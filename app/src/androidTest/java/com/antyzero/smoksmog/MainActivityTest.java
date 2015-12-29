package com.antyzero.smoksmog;


import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.main.MainActivity;
import com.antyzero.smoksmog.utils.rx.BetterIdlingResource;
import com.squareup.spoon.Spoon;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class MainActivityTest {

    @Rule
    public final MockedNetworkActivityTestRule<MainActivity> activityTestRule =
            new MockedNetworkActivityTestRule<>( MainActivity.class );

    private final BetterIdlingResource betterIdlingResource = new BetterIdlingResource();

    @Before
    public void setUp() throws Exception {
        registerIdlingResources( betterIdlingResource );
    }

    @Test
    public void checkCreation() {

        // given
        // ... nothing ...

        // when
        Activity activity = activityTestRule.getActivity();
        Espresso.onView( withId( R.id.indicatorMain ) ).check( matches( isDisplayed() ) );
        // then

        Spoon.screenshot( activity, "Created" );
    }

    @After
    public void tearDown() throws Exception {
        unregisterIdlingResources( betterIdlingResource );
    }
}
