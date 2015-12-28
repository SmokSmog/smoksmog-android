package com.antyzero.smoksmog.logger;

import com.antyzero.smoksmog.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class LoggerModule {

    @Provides
    @Singleton
    public Logger provideLogger() {
        return BuildConfig.DEBUG ? new AndroidLogger() : new CrashlyticsLogger();
    }
}
