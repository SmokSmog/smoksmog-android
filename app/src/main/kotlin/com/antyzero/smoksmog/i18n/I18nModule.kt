package com.antyzero.smoksmog.i18n

import android.content.Context
import com.antyzero.smoksmog.i18n.time.CountdownProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class I18nModule {

    @Provides
    @Singleton
    internal fun provideLocalProvider(context: Context): LocaleProvider {
        return ContextLocaleProvider(context)
    }

    @Provides
    @Singleton
    internal fun provideCountdownProvider(localeProvider: LocaleProvider): CountdownProvider {
        return CountdownProvider(localeProvider)
    }
}
