package com.antyzero.smoksmog.ui.screen.start.item

import android.view.ViewGroup

abstract class ViewDelegate<T : ListViewHolder<R>, R>(val viewType: Int) {

    abstract fun onCreateViewHolder(parent: ViewGroup): T

    fun onBindViewHolder(holder: T, data: R) {
        holder.bind(data)
    }
}
