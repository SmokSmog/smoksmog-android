package com.antyzero.smoksmog.ui.screen.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.antyzero.smoksmog.R

import pl.malopolska.smoksmog.model.Particulate


class HistoryAdapter(private val particulates: List<Particulate>) : RecyclerView.Adapter<ParticulateHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticulateHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ParticulateHistoryViewHolder(inflater.inflate(R.layout.item_chart, parent, false))
    }

    override fun getItemCount(): Int {
        return particulates.size
    }

    override fun onBindViewHolder(holder: ParticulateHistoryViewHolder, position: Int) {
        holder.bind(particulates[position])
    }

}