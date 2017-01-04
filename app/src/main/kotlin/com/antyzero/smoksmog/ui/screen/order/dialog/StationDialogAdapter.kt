package com.antyzero.smoksmog.ui.screen.order.dialog

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.antyzero.smoksmog.R
import pl.malopolska.smoksmog.model.Station


class StationDialogAdapter(private val stationList: List<Station>, private val stationListener: StationDialogAdapter.StationListener) : RecyclerView.Adapter<StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val textView = TextView(parent.context)
        textView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val padding = parent.resources.getDimensionPixelSize(R.dimen.margin_16)
        textView.setPadding(padding, padding, padding, padding)

        return StationViewHolder(textView, object : StationViewHolder.StationClickListener {
            override fun onClick(stationId: Long) {
                stationListener.onStation(stationId)
            }
        })
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stationList[position])
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    interface StationListener {

        fun onStation(stationId: Long)
    }
}
