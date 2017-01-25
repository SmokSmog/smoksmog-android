package com.antyzero.smoksmog.dsl

import android.support.annotation.IdRes
import android.view.View

@Suppress("UNCHECKED_CAST")
fun <T> View.findView(@IdRes id: Int): T = this.findViewById(id) as T