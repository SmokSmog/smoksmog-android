package com.antyzero.smoksmog.screen;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.antyzero.smoksmog.ApplicationComponent;
import com.antyzero.smoksmog.ApplicationModule;
import com.antyzero.smoksmog.DaggerApplicationComponent;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.mock.MockNetworkModule;

/**
 * ActivityTestRule with mocked network communication for faster and more reliable testing
 */
public class MockedNetworkActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    public MockedNetworkActivityTestRule( Class<T> activityClass ) {
        super( activityClass );
    }

    public MockedNetworkActivityTestRule( Class<T> activityClass, boolean initialTouchMode ) {
        super( activityClass, initialTouchMode );
    }

    public MockedNetworkActivityTestRule( Class<T> activityClass, boolean initialTouchMode, boolean launchActivity ) {
        super( activityClass, initialTouchMode, launchActivity );
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();

        SmokSmogApplication application = ( SmokSmogApplication )
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( application ) )
                // Replace network with mocked version
                .networkModule( new MockNetworkModule() )
                .build();

        application.setAppComponent( applicationComponent );
    }

    @Override
    protected void afterActivityFinished() {

        // TODO restore old component - seems wierd, but problem is it's carried on to other tests

        SmokSmogApplication application = ( SmokSmogApplication )
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( application ) )
                .build();

        application.setAppComponent( applicationComponent );

        super.afterActivityFinished();
    }
}
