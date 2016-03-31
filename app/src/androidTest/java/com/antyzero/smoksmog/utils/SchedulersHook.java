package com.antyzero.smoksmog.utils;

import rx.Scheduler;
import rx.plugins.RxJavaSchedulersHook;


public class SchedulersHook extends RxJavaSchedulersHook {

    private final Scheduler scheduler;

    public SchedulersHook( Scheduler scheduler ) {
        this.scheduler = scheduler;
    }

    /*@Override
    public Scheduler getComputationScheduler() {
        return scheduler;
    }

    @Override
    public Scheduler getIOScheduler() {
        return scheduler;
    }*/

    @Override
    public Scheduler getNewThreadScheduler() {
        return scheduler;
    }
}
