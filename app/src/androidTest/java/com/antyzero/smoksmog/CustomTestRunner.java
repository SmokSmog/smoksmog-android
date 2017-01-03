package com.antyzero.smoksmog;

import android.os.AsyncTask;
import android.support.test.runner.AndroidJUnitRunner;

import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * http://collectiveidea.com/blog/archives/2016/10/13/retrofitting-espresso
 */
public class CustomTestRunner extends AndroidJUnitRunner {

    private static final Func1<Scheduler, Scheduler> SCHEDULER = new Func1<Scheduler, Scheduler>() {
        @Override
        public Scheduler call(Scheduler scheduler) {
            return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    };

    @Override
    public void onStart() {
        RxJavaHooks.setOnIOScheduler(SCHEDULER);
        RxJavaHooks.setOnNewThreadScheduler(SCHEDULER);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxJavaHooks.reset();
    }
}
