package com.antyzero.smoksmog;


import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.antyzero.smoksmog.network.NetworkModule;
import com.antyzero.smoksmog.ui.MainActivity;
import com.squareup.spoon.Spoon;

import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.SmokSmog;
import rx.Observable;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>( MainActivity.class );

    @Test
    public void checkCreation() {

        // given
        SmokSmogApplication application = ( SmokSmogApplication )
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( application ) )
                .networkModule( new MockNetworkModule() )
                .build();

        application.setAppComponent( applicationComponent );

        // when
        // ... start app ...

        // then
        Spoon.screenshot( activityTestRule.getActivity(), "Created" );
    }
}
