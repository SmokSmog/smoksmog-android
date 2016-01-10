package com.antyzero.smoksmog;


import android.app.Activity;
import android.content.Intent;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;
import com.antyzero.smoksmog.ui.screen.main.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class HistoryActivityTest {

    @Rule
    public final MockedNetworkActivityTestRule<HistoryActivity> activityTestRule = new HistoryActivityTestRule( true, false );

    @Test
    @UiThreadTest
    public void checkCreation() {

        // given
        activityTestRule.launchActivity( HistoryActivity.fillIntent( new Intent(), 13 ) );
        Activity activity = activityTestRule.getActivity();

        // when
        // do nothing

        // then
        Spoon.screenshot( activity, "Created" );
    }
}
