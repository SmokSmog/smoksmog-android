package com.antyzero.smoksmog.dsl

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

fun RecyclerView.ViewHolder.findViewById(@IdRes id: Int): View = this.itemView.findViewById(id)

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.ViewHolder.findView(@IdRes id: Int): T = this.itemView.findViewById(id) as T