package com.antyzero.smoksmog.ui;

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
    public BaseActivity provideActivity(){
        return mainActivity;
    }

    @Provides
    public GoogleApiClient.ConnectionCallbacks provideConnectionCallbacks(){
        return mainActivity;
    }
}
