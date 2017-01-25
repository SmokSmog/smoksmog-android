package com.antyzero.smoksmog.settings

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsHelper(context: Context) = SettingsHelper(context)
}
