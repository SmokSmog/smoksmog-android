package com.antyzero.smoksmog.ui;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule( BaseActivity baseActivity ) {
        this.baseActivity = baseActivity;
    }

    @Provides
    public BaseActivity provideActivity(){
        return baseActivity;
    }
}
