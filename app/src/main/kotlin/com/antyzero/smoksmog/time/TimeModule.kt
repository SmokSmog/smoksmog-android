package com.antyzero.smoksmog.time

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class TimeModule {

    @Provides
    @Singleton
    internal fun provideCountdownProvider(context: Context): CountdownProvider {
        return CountdownProvider(context)
    }
}
