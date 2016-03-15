package com.antyzero.smoksmog;


import android.app.Activity;
import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;
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
    public void checkCreation() {

        // given
        Activity activity = activityTestRule.launchActivity( HistoryActivity.fillIntent( new Intent(), 13 ) );

        // when
        // do nothing

        // then
        try {
            Spoon.screenshot( activity, "Created" );
        } catch ( Exception e ) {
            System.out.println( e );
        }

    }
}
