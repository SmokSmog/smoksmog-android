package com.antyzero.smoksmog;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.antyzero.smoksmog.mock.MockNetworkModule;

/**
 * ActivityTestRule with mocked network communication for faster and more reliable testing
 */
public class MockedNetworkActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    public MockedNetworkActivityTestRule( Class<T> activityClass ) {
        super( activityClass );
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();

        SmokSmogApplication application = ( SmokSmogApplication )
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( application ) )
                .networkModule( new MockNetworkModule() )
                .build();

        application.setAppComponent( applicationComponent );
    }
}
