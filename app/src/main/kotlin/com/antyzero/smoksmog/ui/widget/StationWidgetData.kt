package com.antyzero.smoksmog.ui.widget

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.antyzero.smoksmog.tag


class StationWidgetData(context: Context) {

    val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(tag(), MODE_PRIVATE)
    }

    fun addWidget(widgetId: Int, stationId: Long) {
        sharedPreferences.edit().putLong(widgetId.toString(), stationId).apply()
    }

    fun removeWidget(widgetId: Int) {
        sharedPreferences.edit().remove(widgetId.toString()).apply()
    }

    fun widgetStationId(widgetId: Int): Long {
        return sharedPreferences.getLong(widgetId.toString(), -1)
    }
}