package com.antyzero.smoksmog.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.setBackgroundColor
import org.joda.time.DateTime
import pl.malopolska.smoksmog.RestClient
import pl.malopolska.smoksmog.model.Station
import smoksmog.air.AirQuality
import smoksmog.air.AirQualityIndex
import smoksmog.logger.Logger
import javax.inject.Inject

class StationWidget : AppWidgetProvider() {

    @Inject lateinit var restClient: RestClient
    @Inject lateinit var logger: Logger

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        SmokSmogApplication.get(context).appComponent.inject(this)

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
