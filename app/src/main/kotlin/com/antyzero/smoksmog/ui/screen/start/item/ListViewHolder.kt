package com.antyzero.smoksmog.ui.screen.start.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class ListViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val context: Context = itemView.context

    open fun bind(data: T) {
    }
}
