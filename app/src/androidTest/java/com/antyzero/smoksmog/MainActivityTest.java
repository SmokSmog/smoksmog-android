package com.antyzero.smoksmog;


import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.main.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class MainActivityTest {

    @Rule
    public final MockedNetworkActivityTestRule<MainActivity> activityTestRule =
            new MockedNetworkActivityTestRule<>( MainActivity.class );

    @Test
    @UiThreadTest
    public void checkCreation() {

        // given
        MainActivity activity = activityTestRule.getActivity();

        // when
        //

        // then
        Spoon.screenshot( activity, "Created" );
    }
}
