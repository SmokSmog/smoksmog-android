package com.antyzero.smoksmog.rules;

import com.antyzero.smoksmog.rules.rx.CustomExecutorScheduler;
import com.antyzero.smoksmog.rules.rx.SchedulersHook;
import com.antyzero.smoksmog.rules.rx.ThreadPoolIdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import rx.plugins.RxJavaTestPlugins;

public class RxSchedulerTestRule implements TestRule {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final CustomExecutorScheduler scheduler;
    private final SchedulersHook schedulersHook;

    public RxSchedulerTestRule() {
        threadPoolExecutor = ( ThreadPoolExecutor ) Executors.newScheduledThreadPool( 16 );
        scheduler = new CustomExecutorScheduler( threadPoolExecutor );
        schedulersHook = new SchedulersHook( scheduler );
    }

    public ThreadPoolIdlingResource getThreadPoolIdlingResource() {
        return new ThreadPoolIdlingResource( threadPoolExecutor ) {
            @Override
            public String getName() {
                return getClass().getSimpleName();
            }
        };
    }

    @Override
    public Statement apply( Statement base, Description description ) {
        return new RxStatement( base, schedulersHook );
    }

    private static class RxStatement extends Statement {
        private final Statement base;
        private final SchedulersHook schedulersHook;

        public RxStatement( Statement base, SchedulersHook schedulersHook ) {
            this.base = base;
            this.schedulersHook = schedulersHook;
        }

        @Override
        public void evaluate() throws Throwable {

            RxJavaTestPlugins.resetPlugins();
            RxJavaTestPlugins.getInstance().registerSchedulersHook( schedulersHook );

            base.evaluate();

            RxJavaTestPlugins.resetPlugins();
        }
    }
}
