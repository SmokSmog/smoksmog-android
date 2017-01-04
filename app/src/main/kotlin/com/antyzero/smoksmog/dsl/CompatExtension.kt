package com.antyzero.smoksmog.dsl

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.text.Html
import android.widget.TextView
import java.util.*

/**
 * Extensions used with
 * - compat utilities
 * - for backward compatibility
 * - in case od deprecated methods
 */

@SuppressLint("NewApi")
fun Context.getCompatColor(@ColorRes colorId: Int): Int = when (Build.VERSION.SDK_INT) {
    in 1..Build.VERSION_CODES.LOLLIPOP_MR1 -> ContextCompat.getColor(this, colorId)
    else -> getColor(colorId) // API23
}

@Suppress("DEPRECATION")
@SuppressLint("NewApi")
fun Configuration.getCompatLocale(): Locale = when (Build.VERSION.SDK_INT) {
    in 1..Build.VERSION_CODES.M -> locale
    else -> locales[0] // API24
}

@SuppressLint("NewApi")
fun TextView.compatFromHtml(@StringRes id: Int) {
    text = when (Build.VERSION.SDK_INT) {
        in 1..Build.VERSION_CODES.M -> Html.fromHtml(context.getString(id))
        else -> Html.fromHtml(context.getString(id), Html.FROM_HTML_MODE_LEGACY) // API24
    }
}