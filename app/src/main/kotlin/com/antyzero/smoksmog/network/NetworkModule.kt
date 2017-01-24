package com.antyzero.smoksmog.network

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.Provides
import pl.malopolska.smoksmog.RestClient
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Provides
    @Singleton
    internal fun provideSmokSmog(context: Context): RestClient {
        return RestClient.Builder(getLocale(context)).apply {
            scheduler = Schedulers.io()
        }.build()
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
