package com.antyzero.smoksmog.ui;

import android.app.Activity;

import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.error.SnackBarErrorReporter;
import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final MainActivity mainActivity;

    public ActivityModule( MainActivity mainActivity ) {
        this.mainActivity = mainActivity;
    }

    @Provides
    public BaseActivity provideBaseActivity() {
        return mainActivity;
    }

    @Provides
    public Activity provideActivity() {
        return mainActivity;
    }

    @Provides
    public ErrorReporter provideErrorReporter( Activity activity ) {
        return new SnackBarErrorReporter( activity );
    }

    @Provides
    public GoogleApiClient.ConnectionCallbacks provideConnectionCallbacks() {
        return mainActivity;
    }
}
