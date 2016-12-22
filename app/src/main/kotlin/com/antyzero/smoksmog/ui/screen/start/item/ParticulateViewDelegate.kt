package com.antyzero.smoksmog.ui.screen.start.item

import android.view.LayoutInflater
import android.view.ViewGroup

import com.antyzero.smoksmog.R

import pl.malopolska.smoksmog.model.Particulate

class ParticulateViewDelegate(viewType: Int) : ViewDelegate<ParticulateViewHolder, Particulate>(viewType) {

    override fun onCreateViewHolder(parent: ViewGroup): ParticulateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ParticulateViewHolder(inflater.inflate(R.layout.item_particulate, parent, false))
    }
}
