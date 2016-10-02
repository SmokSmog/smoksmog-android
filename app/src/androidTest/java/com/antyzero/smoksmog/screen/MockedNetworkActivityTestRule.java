package com.antyzero.smoksmog.screen;

import android.app.Activity;
import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.antyzero.smoksmog.ApplicationComponent;
import com.antyzero.smoksmog.ApplicationModule;
import com.antyzero.smoksmog.DaggerApplicationComponent;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.mock.MockNetworkModule;
import com.antyzero.smoksmog.network.NetworkModule;

/**
 * ActivityTestRule with mocked network communication for faster and more reliable testing
 */
public class MockedNetworkActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    public MockedNetworkActivityTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    public MockedNetworkActivityTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public MockedNetworkActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();

        SmokSmogApplication application = (SmokSmogApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = buildApplicationComponent(application, new MockNetworkModule());

        application.setAppComponent(applicationComponent);
    }

    @Override
    protected void afterActivityFinished() {

        // TODO restore old component - seems weird, but problem is it's carried on to other tests

        SmokSmogApplication application = (SmokSmogApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext();

        ApplicationComponent applicationComponent = buildApplicationComponent(application, new NetworkModule());
        application.setAppComponent(applicationComponent);

        super.afterActivityFinished();
    }

    private ApplicationComponent buildApplicationComponent(Application application, NetworkModule networkModule) {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .networkModule(networkModule)
                .build();
    }
}
