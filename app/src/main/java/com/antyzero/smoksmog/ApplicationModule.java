package com.antyzero.smoksmog;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ApplicationModule {

    private final SmokSmogApplication application;

    public ApplicationModule( SmokSmogApplication application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public SmokSmogApplication provideApplication() {
        return application;
    }

}
