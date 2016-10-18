package com.antyzero.smoksmog.rules;

import android.support.test.espresso.Espresso;

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

    private final SchedulersHook schedulersHook;
    private final ThreadPoolIdlingResource idlingResource;

    public RxSchedulerTestRule() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(16);
        CustomExecutorScheduler scheduler = new CustomExecutorScheduler(threadPoolExecutor);
        schedulersHook = new SchedulersHook(scheduler);
        idlingResource = new ThreadPoolIdlingResource(threadPoolExecutor) {
            @Override
            public String getName() {
                return getClass().getSimpleName();
            }
        };
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new RxStatement(base, this);
    }

    private static class RxStatement extends Statement {
        private final Statement base;
        private final RxSchedulerTestRule testRule;

        public RxStatement(Statement base, RxSchedulerTestRule schedulersHook) {
            this.base = base;
            this.testRule = schedulersHook;
        }

        @Override
        public void evaluate() throws Throwable {

            RxJavaTestPlugins.resetPlugins();
            RxJavaTestPlugins.getInstance().registerSchedulersHook(testRule.schedulersHook);
            Espresso.registerIdlingResources(testRule.idlingResource);

            base.evaluate();

            Espresso.unregisterIdlingResources(testRule.idlingResource);
            RxJavaTestPlugins.resetPlugins();
        }
    }
}
