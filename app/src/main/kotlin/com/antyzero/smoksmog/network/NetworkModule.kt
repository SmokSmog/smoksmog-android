package com.antyzero.smoksmog.network

import android.content.Context
import android.os.Build

import java.util.Locale

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import pl.malopolska.smoksmog.SmokSmog

@Module
open class NetworkModule {

    @Provides
    @Singleton
    internal fun provideSmokSmog(context: Context): SmokSmog {
        return SmokSmog(getLocale(context))
    }

    private fun getLocale(context: Context): Locale {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            return context.resources.configuration.locale
        }
    }
}
