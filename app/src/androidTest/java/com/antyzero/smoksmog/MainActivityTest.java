package com.antyzero.smoksmog;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.ui.MainActivity;
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
    public void checkCreation() {

        // given
        // ... nothing ...

        // when
        // ... start app ...

        // then
        Spoon.screenshot( activityTestRule.getActivity(), "Created" );
    }
}
