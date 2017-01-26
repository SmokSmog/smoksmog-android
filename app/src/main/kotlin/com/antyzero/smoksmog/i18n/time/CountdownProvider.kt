package com.antyzero.smoksmog.i18n.time


import com.antyzero.smoksmog.i18n.LocaleProvider
import java.util.*

class CountdownProvider(private val localeProvider: LocaleProvider) {

    operator fun get(seconds: Int): String = when (localeProvider.get()) {
        LOCALE_POLISH -> PolishCountdown()[seconds]
        else -> EnglishCountdown()[seconds]
    }

    companion object {

        private val LOCALE_POLISH = Locale("pl", "PL")
    }
}
