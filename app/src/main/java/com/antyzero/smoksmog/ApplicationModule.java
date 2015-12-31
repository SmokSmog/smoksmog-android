package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.antyzero.smoksmog.logger.AggregatingLogger;
import com.antyzero.smoksmog.logger.AndroidLogger;
import com.antyzero.smoksmog.logger.CrashlyticsLogger;
import com.antyzero.smoksmog.logger.LevelBlockingLogger;
import com.antyzero.smoksmog.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;

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
}
