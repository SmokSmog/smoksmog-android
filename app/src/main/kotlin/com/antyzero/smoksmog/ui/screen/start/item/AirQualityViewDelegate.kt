package com.antyzero.smoksmog.ui.screen.start.item

import android.view.LayoutInflater
import android.view.ViewGroup

import com.antyzero.smoksmog.R

import pl.malopolska.smoksmog.model.Station

class AirQualityViewDelegate(viewType: Int) : ViewDelegate<AirQualityViewHolder, Station>(viewType) {

    override fun onCreateViewHolder(parent: ViewGroup): AirQualityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AirQualityViewHolder(layoutInflater.inflate(R.layout.item_air_quility, parent, false))
    }
}
