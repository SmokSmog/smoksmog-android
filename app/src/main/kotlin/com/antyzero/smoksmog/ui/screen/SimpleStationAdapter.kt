package com.antyzero.smoksmog.ui.screen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.antyzero.smoksmog.R
import pl.malopolska.smoksmog.model.Station


class SimpleStationAdapter(val listStation: List<Station>, val onStationClick: OnStationClick) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onStationClick.click(listStation[position]) }
        holder.textView.text = listStation[position].name
    }

    override fun getItemCount(): Int {
        return listStation.size
    }
}

interface OnStationClick {
    fun click(station: Station)
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textView: TextView
    var viewHandle: View

    init {
        textView = itemView.findViewById(R.id.textView) as TextView
        viewHandle = itemView.findViewById(R.id.viewHandle)
        viewHandle.visibility = INVISIBLE
    }
}
