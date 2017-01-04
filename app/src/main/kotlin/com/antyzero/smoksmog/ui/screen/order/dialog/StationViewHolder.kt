package com.antyzero.smoksmog.ui.screen.order.dialog

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.malopolska.smoksmog.model.Station

class StationViewHolder(private val textView: TextView, private val stationClickListener: StationViewHolder.StationClickListener) : RecyclerView.ViewHolder(textView) {

    fun bind(station: Station) {
        textView.text = station.name
        itemView.setOnClickListener { stationClickListener.onClick(station.id) }
    }

    interface StationClickListener {

        fun onClick(stationId: Long)
    }
}
