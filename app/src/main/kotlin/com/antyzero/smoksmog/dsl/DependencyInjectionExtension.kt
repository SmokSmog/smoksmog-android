package com.antyzero.smoksmog.dsl

import android.app.Activity
import android.content.Context
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.screen.ActivityModule

/**
 * ApplicationComponent access
 */
fun Any.appComponent(context: Context) = SmokSmogApplication[context].appComponent

fun Context.appComponent() = appComponent(this)

/**
 * ActivityComponent access
 */
fun Any.activityComponent(activity: Activity) = appComponent(activity).plus(ActivityModule(activity))

fun Activity.activityComponent() = activityComponent(this)