package com.antyzero.smoksmog.rules.rx;

import rx.Scheduler;
import rx.plugins.RxJavaSchedulersHook;


public class SchedulersHook extends RxJavaSchedulersHook {

    private final Scheduler scheduler;

    public SchedulersHook(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Scheduler getNewThreadScheduler() {
        return scheduler;
    }
}
