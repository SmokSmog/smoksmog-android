package com.antyzero.smoksmog.screen;


import android.app.Activity;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class SettingsActivityTest {

    @Rule
    public final MockedNetworkActivityTestRule<SettingsActivity> activityTestRule =
            new MockedNetworkActivityTestRule<>( SettingsActivity.class );

    @Test
    @UiThreadTest
    public void checkCreation() {

        // given
        Activity activity = activityTestRule.getActivity();

        // when
        //

        // then
        Spoon.screenshot( activity, "Created" );
    }
}