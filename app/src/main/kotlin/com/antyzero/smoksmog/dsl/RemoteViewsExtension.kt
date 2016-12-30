package com.antyzero.smoksmog.dsl

import android.support.annotation.IdRes
import android.widget.RemoteViews

fun RemoteViews.setBackgroundColor(@IdRes viewId: Int, color: Int) = this.setInt(viewId, "setBackgroundColor", color)