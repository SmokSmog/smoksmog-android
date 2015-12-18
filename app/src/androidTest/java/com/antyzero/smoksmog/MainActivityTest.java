package com.antyzero.smoksmog;


import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.screen.main.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class MainActivityTest {

    @Rule
    public final MockedNetworkActivityTestRule<MainActivity> activityTestRule =
            new MockedNetworkActivityTestRule<>( MainActivity.class );

    @Test
    public void checkCreation() {

        // given
        // ... nothing ...

        // when
        Activity activity = activityTestRule.getActivity();

        // then
        assertThat( activity ).isNotNull();
        Spoon.screenshot( activity, "Created" );
    }
}
