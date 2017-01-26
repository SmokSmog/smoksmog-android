package com.antyzero.smoksmog.i18n

import android.content.Context
import android.os.Build
import java.util.*

internal class ContextLocaleProvider(private val context: Context) : LocaleProvider {

    @Suppress("DEPRECATION")
    override fun get(): Locale = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ){
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}