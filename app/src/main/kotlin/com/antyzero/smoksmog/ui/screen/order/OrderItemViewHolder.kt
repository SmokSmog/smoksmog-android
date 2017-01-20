package com.antyzero.smoksmog.ui.screen.order

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.dsl.findView
import com.antyzero.smoksmog.dsl.findViewById
import com.antyzero.smoksmog.storage.model.Item
import pl.malopolska.smoksmog.model.Station

class OrderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView = findView(R.id.textView)
    val viewHandle: View = findViewById(R.id.viewHandle)

    fun bind(station: Item) {
        textView.text = station.name
    }
}
