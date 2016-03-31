package com.antyzero.smoksmog.screen;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.antyzero.smoksmog.ui.screen.start.StartActivity;
import com.antyzero.smoksmog.utils.CustomExecutorScheduler;
import com.antyzero.smoksmog.utils.SchedulersHook;
import com.antyzero.smoksmog.utils.ThreadPoolIdlingResource;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import rx.Scheduler;
import rx.plugins.RxJavaTestPlugins;


public class StartActivityTest {

    @Rule
    public final ActivityTestRule<StartActivity> activityTestRule;

    private final ThreadPoolExecutor threadPoolExecutor;

    public StartActivityTest() {
        threadPoolExecutor = ( ThreadPoolExecutor ) Executors.newScheduledThreadPool( 16 );
        Scheduler scheduler = new CustomExecutorScheduler( threadPoolExecutor );

        RxJavaTestPlugins.resetPlugins();
        RxJavaTestPlugins.getInstance().registerSchedulersHook( new SchedulersHook( scheduler ) );

        activityTestRule = new ActivityTestRule<>( StartActivity.class );
    }

    @Test
    public void checkCreation() {

        Activity activity = activityTestRule.getActivity();

        Espresso.registerIdlingResources( new ThreadPoolIdlingResource( threadPoolExecutor ) {
            @Override
            public String getName() {
                return ThreadPoolIdlingResource.class.getSimpleName();
            }
        } );

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        Spoon.screenshot( activity, "Creation" );

    }
}
