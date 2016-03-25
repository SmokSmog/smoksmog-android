package com.antyzero.smoksmog.wear;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import smoksmog.logger.AndroidLogger;
import smoksmog.logger.Logger;

@Module
@Singleton
public class ApplicationModule {

    private final Application application;

    public ApplicationModule( Application application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    public Logger providesLogger() {
        return new AndroidLogger();
    }
}
