package com.antyzero.smoksmog.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.dsl.setBackgroundColor
import org.joda.time.DateTime
import pl.malopolska.smoksmog.model.Station
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex

class StationWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach {
            StationWidgetService.update(context, it)
        }
    }

    companion object {

        fun updateWidget(widgetId: Int, context: Context, appWidgetManager: AppWidgetManager, station: Station) {

            val airQualityIndex = AirQualityIndex.calculate(station)
            val airQuality = AirQuality.Companion.findByValue(airQualityIndex)

            val views = RemoteViews(context.packageName, R.layout.widget_station)

            var name = station.name

            if (BuildConfig.DEBUG) {
                name = "Stacja: ${station.name}\nPomiar: ${station.particulates[0].date}\nAktulizacja: ${DateTime.now()}"
            }

            views.setTextViewText(R.id.textViewStation, name)
            views.setTextViewText(R.id.textViewAirQuality, airQualityIndex.format(1))

            views.setTextColor(R.id.textViewAirQuality, airQuality.getColor(context))
            views.setBackgroundColor(R.id.airIndicator1, airQuality.getColor(context))
            views.setBackgroundColor(R.id.airIndicator2, airQuality.getColor(context))

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}

private fun Double.format(digits: Int): CharSequence {
    return java.lang.String.format("%.${digits}f", this)
}
