package com.antyzero.smoksmog.ui.screen.start

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewDelegate
import com.antyzero.smoksmog.ui.screen.start.item.AirQualityViewHolder
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewDelegate
import com.antyzero.smoksmog.ui.screen.start.item.ParticulateViewHolder
import pl.malopolska.smoksmog.model.Station

/**

 */
class StationAdapter(private val stationContainer: List<Station>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val airQualityViewDelegate: AirQualityViewDelegate
    private val particulateViewDelegate: ParticulateViewDelegate

    init {
        setHasStableIds(true)

        airQualityViewDelegate = AirQualityViewDelegate(TYPE_AIR_QUALITY)
        particulateViewDelegate = ParticulateViewDelegate(TYPE_PARTICULATE)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_AIR_QUALITY else TYPE_PARTICULATE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {

            TYPE_AIR_QUALITY -> return airQualityViewDelegate.onCreateViewHolder(parent)
            TYPE_PARTICULATE -> return particulateViewDelegate.onCreateViewHolder(parent)

            else -> throw IllegalStateException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (stationContainer.isEmpty()) {
            return
        }

        val station = stationContainer[0]

        if (position == 0) {
            airQualityViewDelegate.onBindViewHolder(holder as AirQualityViewHolder, station)
        } else if (position > 0) {
            val particulate = station.particulates[position - 1]
            particulateViewDelegate.onBindViewHolder(holder as ParticulateViewHolder, particulate)
        }

    }

    override fun getItemCount(): Int {
        var particulates = 0

        if (!stationContainer.isEmpty()) {
            val station = this.stationContainer[0]
            particulates = station.particulates.size
        }

        return 1 + particulates
    }

    companion object {

        private val TYPE_AIR_QUALITY = Integer.MIN_VALUE
        private val TYPE_PARTICULATE = 0
    }
}
