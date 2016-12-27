package com.antyzero.smoksmog.dsl

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

fun RecyclerView.ViewHolder.findViewById(@IdRes id: Int): View = this.itemView.findViewById(id)


fun <T> RecyclerView.ViewHolder.findView(@IdRes id: Int): T {
    @Suppress("UNCHECKED_CAST")
    return this.itemView.findViewById(id) as T
}