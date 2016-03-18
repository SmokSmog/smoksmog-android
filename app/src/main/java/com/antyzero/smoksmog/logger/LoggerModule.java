package com.antyzero.smoksmog.logger;

import com.antyzero.smoksmog.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import smoksmog.logger.AndroidLogger;
import smoksmog.logger.Logger;

import static com.antyzero.smoksmog.logger.CrashlyticsLogger.ExceptionLevel.ERROR;

@Singleton
@Module
public class LoggerModule {

    @Provides
    @Singleton
    public Logger provideLogger() {
        return BuildConfig.DEBUG ? new AndroidLogger() : new CrashlyticsLogger( ERROR );
    }
}
