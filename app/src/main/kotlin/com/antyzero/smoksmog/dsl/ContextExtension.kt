package com.antyzero.smoksmog.dsl

import android.appwidget.AppWidgetManager
import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(chars: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, chars, duration).show()

fun Context.toast(chars: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, chars, duration).show()

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, stringRes, duration).show()

fun Context.appWidgetManager() = AppWidgetManager.getInstance(this)