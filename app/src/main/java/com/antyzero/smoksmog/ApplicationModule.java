package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;

import com.antyzero.smoksmog.logger.AndroidLogger;
import com.antyzero.smoksmog.logger.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule( Application application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Logger provideLogger(){
        return new AndroidLogger();
    }
}
