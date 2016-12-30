package com.antyzero.smoksmog.logger

import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.logger.CrashlyticsLogger.ExceptionLevel.ERROR
import com.antyzero.smoksmog.user.User
import com.crashlytics.android.core.CrashlyticsCore
import dagger.Module
import dagger.Provides
import smoksmog.logger.AndroidLogger
import smoksmog.logger.Logger
import javax.inject.Singleton

@Singleton
@Module
class LoggerModule {

    @Provides
    @Singleton
    internal fun provideLogger(callback: CrashlyticsLogger.ConfigurationCallback): Logger {
        return if (BuildConfig.DEBUG) AndroidLogger() else CrashlyticsLogger(ERROR, callback)
    }

    @Provides
    @Singleton
    internal fun provideConfigurationCallback(user: User): CrashlyticsLogger.ConfigurationCallback {
        return object : CrashlyticsLogger.ConfigurationCallback {
            override fun onConfiguration(instance: CrashlyticsCore) {
                instance.setUserIdentifier(user.identifier)
            }
        }
    }
}
