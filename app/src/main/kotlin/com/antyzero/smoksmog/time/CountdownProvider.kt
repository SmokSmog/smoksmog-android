package com.antyzero.smoksmog.time


import android.content.Context
import com.antyzero.smoksmog.dsl.getCompatLocale
import java.util.*

class CountdownProvider(private val context: Context) {

    operator fun get(seconds: Int): String = when (context.resources.configuration.getCompatLocale()) {
        LOCALE_POLISH -> PolishCountdown()[seconds]
        else -> EnglishCountdown()[seconds]
    }

    companion object {

        private val LOCALE_POLISH = Locale("pl", "PL")
    }
}
