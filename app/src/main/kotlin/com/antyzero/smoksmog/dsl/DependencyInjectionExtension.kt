package com.antyzero.smoksmog.dsl

import android.app.Activity
import android.content.Context
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.screen.ActivityModule

fun Context.appComponent() = SmokSmogApplication.get(this).appComponent

fun Activity.activityComponent() = appComponent().plus(ActivityModule(this))