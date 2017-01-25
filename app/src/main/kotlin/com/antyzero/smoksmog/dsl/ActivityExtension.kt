package com.antyzero.smoksmog.dsl

import android.app.Activity
import android.content.res.Configuration
import android.support.annotation.IdRes
import android.view.View


fun Activity.fullscreen() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

fun Activity.statusBarHeight(): Int {
    val resource = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resource > 0) {
        return resources.getDimensionPixelSize(resource)
    }
    return 0
}

@Suppress("UNCHECKED_CAST")
fun <T> Activity.findView(@IdRes id: Int): T = this.findViewById(id) as T