package com.antyzero.smoksmog

import android.app.Activity
import android.content.Context
import com.antyzero.smoksmog.ui.screen.ActivityModule

fun Context.appComponent() = SmokSmogApplication.get(this).appComponent

fun Activity.activityComponent() = appComponent().plus(ActivityModule(this))