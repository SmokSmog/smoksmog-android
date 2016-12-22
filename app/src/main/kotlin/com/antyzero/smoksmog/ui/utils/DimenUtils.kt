package com.antyzero.smoksmog.ui.utils

import android.content.Context
import android.content.res.Resources
import android.support.annotation.DimenRes

/**
 * Various tools for positioning manipulation
 */
class DimenUtils private constructor() {

    init {
        throw IllegalAccessError("Utils class")
    }

    companion object {

        fun getNavBarHeight(context: Context, @DimenRes defaultRes: Int): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(if (resourceId > 0) resourceId else defaultRes)
        }

        /**
         * @param context
         * *
         * @return
         */
        fun getNavBarHeight(context: Context): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
        }

        fun getStatusBarHeight(context: Context, @DimenRes defaultRes: Int): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(if (resourceId > 0) resourceId else defaultRes)
        }

        fun getStatusBarHeight(context: Context): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }
    }
}
