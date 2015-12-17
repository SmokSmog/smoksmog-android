package com.antyzero.smoksmog.ui;

import android.app.Activity;

import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.error.SnackBarErrorReporter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule( BaseActivity baseActivity ) {
        this.baseActivity = baseActivity;
    }

    @Provides
    public BaseActivity provideBaseActivity() {
        return baseActivity;
    }

    @Provides
    public Activity provideActivity() {
        return baseActivity;
    }

    @Provides
    public ErrorReporter provideErrorReporter( Activity activity ) {
        return new SnackBarErrorReporter( activity );
    }
}
