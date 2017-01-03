package com.antyzero.smoksmog.dsl

import android.content.Context
import android.support.annotation.DimenRes

private const val idNavBar = "navigation_bar_height"
private const val idStatusBar = "status_bar_height"

fun Context.navBarHeight(): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier(idNavBar, "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

fun Context.navBarHeight(@DimenRes defaultRes: Int): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier(idNavBar, "dimen", "android")
    return resources.getDimensionPixelSize(if (resourceId > 0) resourceId else defaultRes)
}

fun Context.getStatusBarHeight(@DimenRes defaultRes: Int): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier(idStatusBar, "dimen", "android")
    return resources.getDimensionPixelSize(if (resourceId > 0) resourceId else defaultRes)
}

fun Context.getStatusBarHeight(): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier(idStatusBar, "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}