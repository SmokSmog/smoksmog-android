package com.antyzero.smoksmog.ui.screen;

import android.app.Activity;

import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.error.SnackBarErrorReporter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule( Activity activity ) {
        this.activity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    public ErrorReporter provideErrorReporter( Activity activity ) {
        return new SnackBarErrorReporter( activity );
    }
}
