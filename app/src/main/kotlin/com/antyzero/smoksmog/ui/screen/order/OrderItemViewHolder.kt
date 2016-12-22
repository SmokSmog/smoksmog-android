package com.antyzero.smoksmog.ui.screen.order

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.antyzero.smoksmog.R

import butterknife.Bind
import butterknife.ButterKnife
import pl.malopolska.smoksmog.model.Station

class OrderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @Bind(R.id.textView)
    internal var textView: TextView? = null
    @Bind(R.id.viewHandle)
    var handleView: View? = null
        internal set

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(station: Station) {
        textView!!.text = station.name
    }
}
