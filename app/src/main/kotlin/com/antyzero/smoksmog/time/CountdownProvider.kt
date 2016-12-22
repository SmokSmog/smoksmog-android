package com.antyzero.smoksmog.time


import android.content.Context
import java.util.*

class CountdownProvider(private val context: Context) {

    operator fun get(seconds: Int): String {

        val locale = context.resources.configuration.locale

        return when (locale) {
            LOCALE_POLISH -> PolishCountdown()[seconds]
            else -> EnglishCountdown()[seconds]
        }
    }

    companion object {

        private val LOCALE_POLISH = Locale("pl", "PL")
    }
}
